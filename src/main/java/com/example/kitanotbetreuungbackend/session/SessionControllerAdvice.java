package com.example.kitanotbetreuungbackend.session;

import com.example.kitanotbetreuungbackend.user.User;
import com.example.kitanotbetreuungbackend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.Instant;
import java.util.Optional;

@ControllerAdvice
public class SessionControllerAdvice {
    private SessionRepository sessionRepository;
    private final UserRepository userRepository; // Add UserRepository
    private final String defaultUsername; // This will hold the default username


    @Autowired
    public SessionControllerAdvice(SessionRepository sessionRepository, UserRepository userRepository, Environment env) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
        this.defaultUsername = env.getProperty("login.default");
    }

    @ModelAttribute("sessionUser")
    public Optional<User> sessionUser(@CookieValue(value = "sessionId", defaultValue = "") String sessionId) {
        // When you set a username in login.default that user will always be logged in
        if(defaultUsername != null && !defaultUsername.isEmpty()){
            return userRepository.findByName(defaultUsername);
        }

        if (!sessionId.isEmpty()) {
            Optional<Session> sessionOptional = sessionRepository.findByIdAndExpiresAtAfter(sessionId, Instant.now());

            return sessionOptional.map(session -> {
                // new expiresAt value for the current session
                session.setExpiresAt(Instant.now().plusSeconds(7 * 24 * 60 * 60));
                sessionRepository.save(session);

                return session.getUser();
            });
        }
        return Optional.empty();
    }
}
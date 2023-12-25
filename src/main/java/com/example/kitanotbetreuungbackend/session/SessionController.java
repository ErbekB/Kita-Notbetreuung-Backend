package com.example.kitanotbetreuungbackend.session;

import com.example.kitanotbetreuungbackend.user.User;
import com.example.kitanotbetreuungbackend.user.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true", allowedHeaders = "*")
@RestController
public class SessionController {

    private SessionRepository sessionRepository;
    private UserRepository userRepository;

    @Autowired
    public SessionController(SessionRepository sessionRepository, UserRepository userRepository) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO login, HttpServletResponse response) {
        Optional<User> userOptional = userRepository.findByNameAndPasswort(login.getName(), login.getPasswort());

        if (userOptional.isPresent()) {
            //expires one week from now
            Session session = new Session(userOptional.get(), Instant.now().plusSeconds(7*24*60*60));
            sessionRepository.save(session);

            //store the session ID in a cookie
            Cookie cookie = new Cookie("sessionId", session.getId());
            response.addCookie(cookie);

            // Login successful
            return new LoginResponseDTO(login.getName());
        }

        // When login-does not work
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No such username or wrong password");
    }

    @PostMapping("/logout")
    public LogoutResponseDTO logout(@CookieValue(value = "sessionId", defaultValue = "") String sessionId, HttpServletResponse response) {
        Optional<Session> sessionOptional = sessionRepository.findByIdAndExpiresAtAfter(sessionId, Instant.now());
        // Delete session in database
        sessionOptional.ifPresent(session -> sessionRepository.delete(session));

        // Delete session cookie at client
        Cookie cookie = new Cookie("sessionId", "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return new LogoutResponseDTO();
    }

    @GetMapping("/auth-status")
    public ResponseEntity<?> authStatus(@ModelAttribute("sessionUser") Optional<User> sessionUserOptional) {
        if (sessionUserOptional.isPresent()) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}

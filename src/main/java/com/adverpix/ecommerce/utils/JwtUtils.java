package com.adverpix.ecommerce.utils;

import com.adverpix.ecommerce.entity.User;
import com.adverpix.ecommerce.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtUtils {
    private static final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private final int jwtExpirationMs = 86400000; // 1 day in milliseconds

    private final UserRepository userRepository;

    public JwtUtils(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Generate Token
    public String generateToken(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            Long userId = user.get().getId(); // Assuming User entity has a getId() method

            // Add username and userId to token
            return Jwts.builder()
                    .setSubject(username)
                    .claim("userId", userId) // Add user ID to claims
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
                    .signWith(secretKey)
                    .compact();
        }
        return null; // or throw an exception if user is not found
    }

    // Extract Username
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Extract User ID
    public Long extractUserId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("userId", Long.class); // Extract user ID from claims
    }

    // Token Validation
    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}

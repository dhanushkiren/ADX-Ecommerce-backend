package com.adverpix.ecommerce.utils;

import com.adverpix.ecommerce.entity.User;
import com.adverpix.ecommerce.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Component
public class JwtUtils {
    private static final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    private final int jwtExpirationMs = 86400000;

    private UserRepository userRepository;

    public JwtUtils(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    // Generate Token
    public String generateToken(String username){
        Optional<User> user = userRepository.findByUsername(username);

        //Add username to token
        return Jwts.builder().setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(secretKey).compact();
    }

    //extract Username
    public String extractUsername(String token){
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
    }


    //Extract role
//    public Set<String> extractRoles(String token){
//        String roleString = Jwts.parserBuilder().setSigningKey(secretKey)
//                .build().parseClaimsJws(token).getBody()
//                .get("username",String.class);
//
//        return Set.of(roleString);
//    }

    //Token Validation
    public boolean isTokenValid(String token){



        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e ){
            return false;
        }
    }
}

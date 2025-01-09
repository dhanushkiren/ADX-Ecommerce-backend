package com.adverpix.ecommerce.controller;

import com.adverpix.ecommerce.dto.LoginDto;
import com.adverpix.ecommerce.dto.RegisterDto;
import com.adverpix.ecommerce.entity.User;
import com.adverpix.ecommerce.exception.CustomException;
import com.adverpix.ecommerce.repository.UserRepository;
import com.adverpix.ecommerce.utils.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Register User API
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerUser){

        //check if username already exists
        if(userRepository.findByUsername(registerUser.getUsername()).isPresent()){
            return ResponseEntity.badRequest().body("Username is already Exists");
        }

        User newUser = new User();
        newUser.setUsername(registerUser.getUsername());
        String encodedPassword = passwordEncoder.encode(registerUser.getPassword());
        newUser.setPassword(encodedPassword);
        newUser.setMobile(String.valueOf(registerUser.getMobile()));
        newUser.setFirstName(registerUser.getFirstName());
        newUser.setLastName(registerUser.getLastName());

        userRepository.save(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("User Registered Successfully");
    }

    // login API
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginRequest){

        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new CustomException("Incorrect username or password", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new CustomException("User not found", HttpStatus.NOT_FOUND);
        }
        String token = jwtUtils.generateToken(loginRequest.getUsername());
        return ResponseEntity.ok(token);
    }
}

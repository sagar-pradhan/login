package com.dev.login.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.login.dto.LoginUserDto;
import com.dev.login.dto.RegisterUserDto;
import com.dev.login.dto.VerifyUserDto;
import com.dev.login.model.User;
import com.dev.login.response.LoginResponse;
import com.dev.login.service.AuthenticationService;
import com.dev.login.service.JwtService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor    
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody RegisterUserDto registerUserDto) {
        try {
            User user = authenticationService.signUp(registerUserDto);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginUserDto loginUserDto) {
        try {
            User authentiacatedUser = authenticationService.authenticate(loginUserDto);
            String jwtToken = jwtService.generateToken(authentiacatedUser);
            LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime());
            return ResponseEntity.ok(loginResponse);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestBody VerifyUserDto verifyUserDto) {
        try {
            authenticationService.verifyUser(verifyUserDto);
            return ResponseEntity.ok("User verified successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/resend")
    public ResponseEntity<?> resend(@RequestBody String email) {
         VerifyUserDto verifyUserDto = new VerifyUserDto();
         verifyUserDto.setEmail(email);
        try {
            authenticationService.resendVerificationCode(email);
            return ResponseEntity.ok("Verification email resent successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    
}

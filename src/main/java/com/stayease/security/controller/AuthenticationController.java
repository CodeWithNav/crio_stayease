package com.stayease.security.controller;


import com.stayease.repository.UserRepository;
import com.stayease.security.dto.JwtTokenDto;
import com.stayease.security.dto.SignInDto;
import com.stayease.security.dto.SignUpDto;
import com.stayease.security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {


    final AuthenticationService authenticationService;
    final UserRepository userRepository;

    @GetMapping("auth")
    public ResponseEntity<String> auth(){
        return ResponseEntity.ok("Auth");
    }

    @PostMapping("register")
    public ResponseEntity<JwtTokenDto> signUp(@RequestBody SignUpDto signUpDto){
        authenticationService.signUp(signUpDto);
        return signIn(new SignInDto(signUpDto.getEmail(), signUpDto.getPassword()));
    }

    @PostMapping("login")
    public ResponseEntity<JwtTokenDto> signIn(@RequestBody SignInDto signInDto){
        log.info(signInDto);
        var user = userRepository.findByEmail(signInDto.getEmail());
        log.info(user);
        return ResponseEntity.ok(authenticationService.signIn(signInDto));
    }

}

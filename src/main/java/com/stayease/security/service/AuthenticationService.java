package com.stayease.security.service;


import com.stayease.exceptions.AlreadyExistException;
import com.stayease.model.User;
import com.stayease.repository.UserRepository;
import com.stayease.security.dto.JwtTokenDto;
import com.stayease.security.dto.SignInDto;
import com.stayease.security.dto.SignUpDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;
    final TokenService tokenService;
    final AuthenticationManager authenticationManager;


    public JwtTokenDto signIn(SignInDto signInDto){
        log.info(signInDto);
        var usernamePassword = new UsernamePasswordAuthenticationToken(signInDto.getEmail(),signInDto.getPassword());
        log.info(usernamePassword);
        var authUser = authenticationManager.authenticate(usernamePassword);
        log.error(authUser);
        return new JwtTokenDto(tokenService.generateAccessToken((User) authUser.getPrincipal()));
    }

    public UserDetails signUp(SignUpDto signUpDto) {
        if(userRepository.findByEmail(signUpDto.getEmail()).isPresent()){
            throw new AlreadyExistException("Username already exists");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(signUpDto.getPassword());

        return  userRepository.save(
                User.builder()
                        .firstName(signUpDto.getFirstName())
                        .lastName(signUpDto.getLastName())
                        .email(signUpDto.getEmail())
                        .role(signUpDto.getRole())
                        .password(encryptedPassword)
                        .build()
                );
    }



}

package com.stayease.security.config;

import com.stayease.repository.UserRepository;
import com.stayease.security.filters.SecurityFilter;
import com.stayease.security.service.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class BaseSecurity {


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
                                            TokenService tokenService,
                                            UserRepository userRepository
    ) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS
                )).authorizeHttpRequests(
                        authorizer -> authorizer
                                .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/hotels/**").permitAll()
                                .requestMatchers(HttpMethod.POST,"/hotels").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE,"/hotels/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST,"/hotels/{id}/book").hasRole("CUSTOMER")
                                .requestMatchers(HttpMethod.PUT, "/hotels/{id}").hasRole("MANAGER")
                                .requestMatchers(HttpMethod.DELETE, "/hotels/bookings/{bookingId}").hasRole("MANAGER")
                                .anyRequest().authenticated()
                )
                .addFilterBefore(new SecurityFilter(tokenService, userRepository), UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
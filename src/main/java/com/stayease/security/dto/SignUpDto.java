package com.stayease.security.dto;

import com.stayease.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDto {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private UserRole role;
}

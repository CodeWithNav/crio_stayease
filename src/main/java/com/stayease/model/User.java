package com.stayease.model;

import com.stayease.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    private String firstName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.CUSTOMER;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(role == UserRole.ADMIN) {
            return List.of(
                    (GrantedAuthority) () -> "ROLE_ADMIN"
            );
        }
        if(role == UserRole.MANAGER) {
            return List.of(
                    (GrantedAuthority) () -> "ROLE_MANAGER"
            );
        }
        return List.of(
                (GrantedAuthority) () -> "ROLE_CUSTOMER"
        );
    }

    @Override
    public String getUsername() {
        return email;
    }
}

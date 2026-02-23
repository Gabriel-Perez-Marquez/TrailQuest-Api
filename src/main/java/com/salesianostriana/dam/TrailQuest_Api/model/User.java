package com.salesianostriana.dam.TrailQuest_Api.model;

import com.salesianostriana.dam.TrailQuest_Api.validation.StrongPassword;
import com.salesianostriana.dam.TrailQuest_Api.validation.UniqueEmail;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;


@Entity
@Table(name = "user_entity")
@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {


    @Id
    @GeneratedValue
    private UUID id;

    @NotBlank(message = "{user.username.notblank}")
    @Size(min = 4, max = 20, message = "{user.username.size}")
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank(message = "{user.password.notblank }")
    @Size(min = 8, message = "{user.password.size}")
    @StrongPassword
    private String password;

    @Email(message = "{user.email.email}")
    @NotBlank(message = "{user.email.notblank}")
    @Column(unique = true, nullable = false)
    @UniqueEmail
    private String email;

    @NotEmpty(message = "{user.roles.notempty}")
    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Set<UserRole> roles = new HashSet<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_"+role.name()))
                .collect(Collectors.toSet());
    }
}

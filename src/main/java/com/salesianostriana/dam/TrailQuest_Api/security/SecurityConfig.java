package com.salesianostriana.dam.TrailQuest_Api.security;

import com.salesianostriana.dam.TrailQuest_Api.model.User;
import com.salesianostriana.dam.TrailQuest_Api.model.UserRole;
import com.salesianostriana.dam.TrailQuest_Api.repository.UserRepository;
import com.salesianostriana.dam.TrailQuest_Api.security.error.JwtAccessDeniedHandler;
import com.salesianostriana.dam.TrailQuest_Api.security.error.JwtAuthenticationEntryPoint;
import com.salesianostriana.dam.TrailQuest_Api.security.jwt.JwtAuthenticationFilter;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;
import java.util.Set;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    // Este metodo que lo copie y `pegue gabriel para que salga que lo ha hecho el
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // Desde aqui hasta el siguiente comentario que lo copie y lo pegue gabriel para que salga que lo ha hecho el
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(basic -> basic.disable()) // Hugo: Desactiva login básico
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .cors(corsConf -> {
                    CorsConfiguration configuration = new CorsConfiguration();
                    configuration.setAllowedOrigins(List.of("*")); // Ajustar en producción
                    configuration.setAllowedMethods(List.of("GET","POST", "PUT", "DELETE"));
                    configuration.setAllowedHeaders(List.of("*"));
                    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                    source.registerCorsConfiguration("/**", configuration);
                    corsConf.configurationSource(source);
                })
                // Hasta aqui
                .exceptionHandling(excepz ->
                        excepz
                                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                                .accessDeniedHandler(jwtAccessDeniedHandler)
                );

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**", "/h2-console/**", "/error").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/pois/route/**", "/api/routes/**").permitAll()
                .requestMatchers("/api/pois/**").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
        );

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }

    @PostConstruct
    void init() {
        if (userRepository.count() == 0) {
            userRepository.save(User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("12345678"))
                    .email("admin@trailquest.com")
                    .roles(Set.of(UserRole.ADMIN))
                    .build());

            userRepository.save(User.builder()
                    .username("user")
                    .password(passwordEncoder.encode("12345678"))
                    .email("user@trailquest.com")
                    .roles(Set.of(UserRole.USER))
                    .build());
        }
    }
}

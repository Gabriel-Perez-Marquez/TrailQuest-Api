package com.salesianostriana.dam.TrailQuest_Api.security.jwt;


import com.salesianostriana.dam.TrailQuest_Api.model.User;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtAccessTokenService {

    public static final String TOKEN_TYPE = "JWT";
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";


    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.duration}")
    private long jwtLifeInSeconds;

    private JwtParser jwtParser;

    private SecretKey secretKey;

    @PostConstruct
    public void init() {

        secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());

        jwtParser = Jwts.parser()
                .verifyWith(secretKey)
                .build();

    }

    public String generateAccessToken(User user) {
        return generateAccessToken(user.getId().toString());
    }

    public String generateAccessToken(String uuid) {
        return Jwts.builder()
                .header().setType(TOKEN_TYPE)
                .and()
                .subject(uuid)
                .issuedAt(new Date())
                .expiration(Date.from(
                        Instant.now()
                                .plusSeconds(jwtLifeInSeconds)
                ))
                .signWith(secretKey)
                .compact();
    }




}

package com.gr.hua.dit.project2025.StreetFoodGo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;

/**
 *  JwtService is responsible for generating and parsing JSON web tokens (JWTs)
 */

@Service
public class JwtService {
    // secret key used to sign JWT tokens
    private final Key key;
    // Issuer of the JWT token
    private final String issuer;
    // Audience for the JWT token
    private final String audience;
    // Time-to-live for the token in minutes
    private final long ttlMinutes;

    public JwtService(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.issuer}") String issuer,
            @Value("${app.jwt.audience}") String audience,
            @Value("${app.jwt.ttl-minutes}") long ttlMinutes
    ) {
        // Validate input values
        if (secret == null || secret.isBlank())
            throw new IllegalArgumentException("JWT secret must not be blank");
        if (issuer == null || issuer.isBlank())
            throw new IllegalArgumentException("JWT issuer must not be blank");
        if (audience == null || audience.isBlank())
            throw new IllegalArgumentException("JWT audience must not be blank");
        if (ttlMinutes <= 0)
            throw new IllegalArgumentException("JWT ttlMinutes must be > 0");
        // Generate the signing key from the secret
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.issuer = issuer;
        this.audience = audience;
        this.ttlMinutes = ttlMinutes;
    }
    // Issues a JWT token for a given subject (username) and roles.
    public String issue(String subject, Collection<String> roles) {
        Instant now = Instant.now();

        return Jwts.builder()
                .setSubject(subject)
                .setIssuer(issuer)
                .setAudience(audience)
                .claim("roles", roles)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(Duration.ofMinutes(ttlMinutes))))
                .signWith(key) // algorithm inferred (HS256)
                .compact();
    }
    // Parses and validates a JWT token

    public Claims parse(String token) {
        return Jwts.parserBuilder()
                .requireIssuer(issuer)
                .requireAudience(audience)
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
package com.fitmesh.fitmeshbackend.infrastructure.security;

import com.fitmesh.fitmeshbackend.domain.enums.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class JwtService {

    private final SecretKey secretKey;
    private final long expirationMs;

    public JwtService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration-ms}") long expirationMs
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMs = expirationMs;
    }

    /**
     * Generate JWT token for a user
     */
    public String generateToken(String userId, String email, Set<UserRole> roles) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMs);

        List<String> roleNames = roles.stream()
                .map(Enum::name)
                .collect(Collectors.toList());

        return Jwts.builder()
                .subject(userId)
                .claim("email", email)
                .claim("roles", roleNames)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }

    /**
     * Extract userId from token
     */
    public String extractUserId(String token) {
        return extractClaims(token).getSubject();
    }

    /**
     * Extract email from token
     */
    public String extractEmail(String token) {
        return extractClaims(token).get("email", String.class);
    }

    /**
     * Extract roles from token
     */
    @SuppressWarnings("unchecked")
    public Set<UserRole> extractRoles(String token) {
        Claims claims = extractClaims(token);
        List<String> roleNames = claims.get("roles", List.class);

        return roleNames.stream()
                .map(UserRole::valueOf)
                .collect(Collectors.toSet());
    }

    /**
     * Validate token
     */
    public boolean isTokenValid(String token) {
        try {
            extractClaims(token);
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}

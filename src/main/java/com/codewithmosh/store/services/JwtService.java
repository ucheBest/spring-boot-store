package com.codewithmosh.store.services;

import com.codewithmosh.store.UserModel;
import com.codewithmosh.store.config.JwtConfig;
import com.codewithmosh.store.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@AllArgsConstructor
public class JwtService {

    private final JwtConfig jwtConfig;

    public String generateAccessToken(User user) {
        return generateToken(user, jwtConfig.getAccessTokenExpiration());
    }

    public String generateRefreshToken(User user) {
        return generateToken(user, jwtConfig.getRefreshTokenExpiration());
    }

    public String generateToken(User user, long tokenExpiration) {
        return Jwts.builder()
            .subject(String.valueOf(user.getId()))
            .claim("name", user.getName())
            .claim("email", user.getEmail())
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + 1000 * tokenExpiration))
            .signWith(jwtConfig.getSecretKey())
            .compact();
    }

    public boolean validateToken(String token) {
        try {
            var claims = getUserClaims(token);
            return claims.getExpiration().after(new Date());
        } catch (JwtException e) {
            return false;
        }
    }

    private Claims getUserClaims(String token) {
        return Jwts.parser()
            .verifyWith(jwtConfig.getSecretKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    public Long getUserIdFromToken(String token) {
        return Long.valueOf(getUserClaims(token).getSubject());
    }
}

package com.cibertec.turismo.service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private static final String SECRET = Base64.getEncoder().encodeToString("secretoSuperSegurosecretoSuperSegurosecretoSuperSeguro".getBytes(StandardCharsets.UTF_8));
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));

    public String generateToken(String email) {
        return Jwts.builder()
            .subject(email) 
            .issuedAt(new Date(System.currentTimeMillis())) 
            .expiration(new Date(System.currentTimeMillis() + 86400000)) 
            .signWith(SECRET_KEY)
            .compact();
    }

    public String extractUsername(String token) {
        Jws<Claims> claimsJws = Jwts.parser()
            .verifyWith(SECRET_KEY)
            .build()
            .parseSignedClaims(token);
        
        return claimsJws.getPayload().getSubject();
    }

    public boolean isTokenValid(String token, String email) {
        return extractUsername(token).equals(email);
    }
}

package com.cibertec.turismo.service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Service
public class JwtService {

    private static final String SECRET = Base64.getEncoder().encodeToString("secretoSuperSegurosecretoSuperSegurosecretoSuperSeguro".getBytes(StandardCharsets.UTF_8));
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));

    public String generateToken(String email, String role) {
        return Jwts.builder()
            .subject(email) 
            .claim("role", role)
            .issuedAt(new Date(System.currentTimeMillis())) 
            .expiration(new Date(System.currentTimeMillis() + 86400000)) 
            .signWith(SECRET_KEY)
            .compact();
    }

    public String extractUsername(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token);
            return claimsJws.getPayload().getSubject();
        } catch (SignatureException e) {
            throw new SignatureException("Firma JWT inválida.");
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtException(null, null, "Token expirado.");
        } catch (MalformedJwtException e) {
            throw new MalformedJwtException("Token mal formado.");
        } catch (Exception e) {
            throw new RuntimeException("No se pudo validar el token.");
        }
    }

    public String extractRole(String token) {
        return extractClaims(token).get("role", String.class);
    }

    public boolean isTokenValid(String token, String email) {
        try {
            return extractUsername(token).equals(email);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private Claims extractClaims(String token) {
        Jws<Claims> claimsJws = Jwts.parser()
            .verifyWith(SECRET_KEY)
            .build()
            .parseSignedClaims(token);
        
        return claimsJws.getPayload();
    }
}

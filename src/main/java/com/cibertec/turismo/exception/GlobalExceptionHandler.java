package com.cibertec.turismo.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<String> handleSignatureException(SignatureException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error: Firma JWT inválida.");
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<String> handleExpiredJwtException(ExpiredJwtException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error: Token expirado.");
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<String> handleMalformedJwtException(MalformedJwtException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error: Token mal formado.");
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleGeneralException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error: No se pudo validar el token.");
    }
}
package com.cibertec.turismo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.turismo.model.AuthRequest;
import com.cibertec.turismo.model.Usuario;
import com.cibertec.turismo.service.implementation.AuthServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthServiceImpl authService;
	
	@PostMapping("/register")
	public ResponseEntity<?> registrar(@Valid @RequestBody Usuario usuario, BindingResult result) {
	    if (result.hasErrors()) {
	        List<String> errores = result.getFieldErrors()
	                .stream()
	                .map(error -> error.getField() + ": " + error.getDefaultMessage())
	                .collect(Collectors.toList());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
	    }

	    try {
	        authService.registrar(usuario);
	        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado correctamente.");
	    } catch (RuntimeException e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al registrar usuario: " + e.getMessage());
	    }
	}
	
	@PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest request, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errores = result.getFieldErrors()
                    .stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
        }

        try {
            String token = authService.login(request.getEmail(), request.getPassword());
            return ResponseEntity.ok(token);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error en la autenticación: " + e.getMessage());
        }
    }
}
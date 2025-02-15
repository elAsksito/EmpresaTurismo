package com.cibertec.turismo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.turismo.model.AuthRequest;
import com.cibertec.turismo.model.Usuario;
import com.cibertec.turismo.service.implementation.AuthServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	
	@Autowired
	private AuthServiceImpl authService;
	
	@PostMapping("/register")
	public ResponseEntity<String> registrar(@RequestBody Usuario usuario) {
	    try {
	        authService.registrar(usuario);
	        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado correctamente.");
	    } catch (RuntimeException e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al registrar usuario: " + e.getMessage());
	    }
	}

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request.getEmail(), request.getPassword()));
    }
}
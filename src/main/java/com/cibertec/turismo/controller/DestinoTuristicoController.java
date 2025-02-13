package com.cibertec.turismo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.turismo.model.DestinoTuristico;
import com.cibertec.turismo.service.interfaces.IDestinoTuristicoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/destinos")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class DestinoTuristicoController {
	
	private final IDestinoTuristicoService destinoService;

    @PostMapping
    public ResponseEntity<DestinoTuristico> crearDestino(@RequestBody DestinoTuristico destino) {
        return ResponseEntity.ok(destinoService.crearDestino(destino));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<DestinoTuristico>> listarDestinos() {
        return ResponseEntity.ok(destinoService.listarDestinos());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<DestinoTuristico> obtenerDestinoPorId(@PathVariable Long id) {
        return destinoService.obtenerDestinoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
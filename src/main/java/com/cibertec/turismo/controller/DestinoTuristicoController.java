package com.cibertec.turismo.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.cibertec.turismo.model.DestinoTuristico;
import com.cibertec.turismo.service.interfaces.IDestinoTuristicoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/destinos")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class DestinoTuristicoController {

    private final IDestinoTuristicoService destinoService;

    @PostMapping
    public ResponseEntity<?> crearDestino(@Valid @RequestBody DestinoTuristico destino, BindingResult result) {
    	if (result.hasErrors()) {
	        List<String> errores = result.getFieldErrors()
	                .stream()
	                .map(error -> error.getField() + ": " + error.getDefaultMessage())
	                .collect(Collectors.toList());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
	    }
    	
    	try {
    		destinoService.crearDestino(destino);
            return ResponseEntity.status(HttpStatus.CREATED).body("Destino creado correctamente.");
	    } catch (RuntimeException e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al registrar destino turistico: " + e.getMessage());
	    }    
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<DestinoTuristico>> listarDestinos() {
        return ResponseEntity.ok(destinoService.listarDestinos());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> obtenerDestinoPorId(@PathVariable Long id) {
        Optional<DestinoTuristico> destinoOpt = destinoService.obtenerDestinoPorId(id);

        if (destinoOpt.isPresent()) {
            return ResponseEntity.ok(destinoOpt.get());
        } else {
            return ResponseEntity.status(404).body("Destino no encontrado.");
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarDestino(
            @PathVariable Long id,
            @Valid @RequestBody DestinoTuristico destino, BindingResult result) {

    	if (result.hasErrors()) {
	        List<String> errores = result.getFieldErrors()
	                .stream()
	                .map(error -> error.getField() + ": " + error.getDefaultMessage())
	                .collect(Collectors.toList());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
	    }
    	
        if (id == null || id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El ID del destino no es válido.");
        }

        Optional<DestinoTuristico> destinoOpt = destinoService.obtenerDestinoPorId(id);
        if (destinoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Destino no encontrado.");
        }
        
        destinoService.actualizarDestino(id, destino);
        return ResponseEntity.ok("Destino actualizado correctamente.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarDestino(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El ID del destino no es válido.");
        }

        Optional<DestinoTuristico> destinoOpt = destinoService.obtenerDestinoPorId(id);
        if (destinoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Destino no encontrado.");
        }

        boolean eliminado = destinoService.eliminarDestino(id);
        if (!eliminado) {
            return ResponseEntity.badRequest().body("No se puede eliminar el destino porque tiene reservas asociadas.");
        }
        return ResponseEntity.ok("Destino eliminado correctamente.");
    }

    @GetMapping("/buscar")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> buscarPorNombre(@RequestParam String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El nombre del destino no puede estar vacío.");
        }
        
        List<DestinoTuristico> resultados = destinoService.buscarPorNombre(nombre);
        if (resultados.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron destinos con ese nombre.");
        }
        return ResponseEntity.ok(resultados);
    }
}

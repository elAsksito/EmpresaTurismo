package com.cibertec.turismo.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.cibertec.turismo.model.Reserva;
import com.cibertec.turismo.model.Usuario;
import com.cibertec.turismo.service.interfaces.IReservaService;
import com.cibertec.turismo.service.interfaces.IUsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/reservas")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public class ReservaController {
    
    private final IReservaService reservaService;
    private final IUsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<?> crearReserva(@Valid @RequestBody Reserva reserva, 
    		Authentication authentication, BindingResult result) {
    	
    	if (result.hasErrors()) {
	        List<String> errores = result.getFieldErrors()
	                .stream()
	                .map(error -> error.getField() + ": " + error.getDefaultMessage())
	                .collect(Collectors.toList());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
	    }
    	
        Usuario usuario = usuarioService.obtenerUsuarioPorEmail(authentication.getName()).orElseThrow(
            () -> new RuntimeException("Usuario no encontrado")
        );

        if (usuario.getRole().equalsIgnoreCase("ADMIN")) {
            if (reserva.getUsuario() == null || reserva.getUsuario().getId() == null) {
                return ResponseEntity.badRequest().body("El ID de usuario es obligatorio para un administrador.");
            }
        } else {
            if (reserva.getUsuario() != null && reserva.getUsuario().getId() != null) {
                return ResponseEntity.badRequest().body("El ID de usuario no es necesario para usuarios normales.");
            }
            reserva.setUsuario(usuario);
        }

        reservaService.crearReserva(reserva);
        return ResponseEntity.status(201).body("Reserva creada exitosamente.");
    }

    @GetMapping
    public ResponseEntity<List<Reserva>> listarReservas(Authentication authentication) {
        Usuario usuario = usuarioService.obtenerUsuarioPorEmail(authentication.getName()).orElseThrow(
                () -> new RuntimeException("Usuario no encontrado")
        );

        List<Reserva> reservas;
        
        if (usuario.getRole().equalsIgnoreCase("ADMIN")) {
            reservas = reservaService.listarReservas();
        } else {
            reservas = reservaService.listarReservasPorUsuario(usuario.getId());
        }

        return ResponseEntity.ok(reservas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerReservaPorId(@PathVariable Long id, Authentication authentication) {
        Usuario usuario = usuarioService.obtenerUsuarioPorEmail(authentication.getName()).orElseThrow(
                () -> new RuntimeException("Usuario no encontrado")
        );

        Reserva reserva = reservaService.obtenerReservaPorId(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        if (usuario.getRole().equalsIgnoreCase("ADMIN") || reserva.getUsuario().getId().equals(usuario.getId())) {
            return ResponseEntity.ok(reserva);
        }

        return ResponseEntity.status(403).body("Acceso denegado.");
    }

    
    @GetMapping("/usuario/{usuarioId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Reserva>> obtenerReservasPorUsuario(@PathVariable Long usuarioId) {
        List<Reserva> reservas = reservaService.listarReservasPorUsuario(usuarioId);
        return ResponseEntity.ok(reservas);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarReserva(@PathVariable Long id, 
    		@Valid @RequestBody Reserva reserva, Authentication authentication, BindingResult result) {
    	
    	if (result.hasErrors()) {
	        List<String> errores = result.getFieldErrors()
	                .stream()
	                .map(error -> error.getField() + ": " + error.getDefaultMessage())
	                .collect(Collectors.toList());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
	    }
    	
        Usuario usuario = usuarioService.obtenerUsuarioPorEmail(authentication.getName()).orElseThrow(
                () -> new RuntimeException("Usuario no encontrado")
        );

        Optional<Reserva> reservaOpt = reservaService.obtenerReservaPorId(id);

        if (reservaOpt.isPresent()) {
            Reserva reservaExistente = reservaOpt.get();

            if (usuario.getRole().equalsIgnoreCase("ADMIN") || reservaExistente.getUsuario().getId().equals(usuario.getId())) {
                reservaService.actualizarReserva(id, reserva);
                return ResponseEntity.ok("Reserva actualizada correctamente.");
            } else {
                return ResponseEntity.status(403).body("Acceso denegado.");
            }
        }
        return ResponseEntity.status(404).body("Reserva no encontrada.");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> eliminarReserva(@PathVariable Long id) {
        reservaService.eliminarReserva(id);
        return ResponseEntity.ok("Reserva eliminada correctamente.");
    }
}
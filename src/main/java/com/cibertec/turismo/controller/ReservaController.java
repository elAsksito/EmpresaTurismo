package com.cibertec.turismo.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.cibertec.turismo.model.Reserva;
import com.cibertec.turismo.model.Usuario;
import com.cibertec.turismo.service.interfaces.IReservaService;
import com.cibertec.turismo.service.interfaces.IUsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/reservas")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public class ReservaController {
    
    private final IReservaService reservaService;
    private final IUsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<String> crearReserva(@RequestBody Reserva reserva, Authentication authentication) {
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
    public ResponseEntity<String> listarReservas(Authentication authentication) {
        Usuario usuario = usuarioService.obtenerUsuarioPorEmail(authentication.getName()).orElseThrow(
                () -> new RuntimeException("Usuario no encontrado")
        );

        if (usuario.getRole().equalsIgnoreCase("ADMIN")) {
            return ResponseEntity.ok("Lista de todas las reservas obtenida correctamente.");
        } else {
            return ResponseEntity.ok("Lista de reservas del usuario obtenida correctamente.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> obtenerReservaPorId(@PathVariable Long id, Authentication authentication) {
        Usuario usuario = usuarioService.obtenerUsuarioPorEmail(authentication.getName()).orElseThrow(
                () -> new RuntimeException("Usuario no encontrado")
        );

        Optional<Reserva> reservaOpt = reservaService.obtenerReservaPorId(id);

        if (reservaOpt.isPresent()) {
            Reserva reserva = reservaOpt.get();
            if (usuario.getRole().equalsIgnoreCase("ADMIN") || reserva.getUsuario().getId().equals(usuario.getId())) {
                return ResponseEntity.ok("Reserva encontrada exitosamente.");
            }
        }
        return ResponseEntity.status(403).body("Acceso denegado.");
    }
    
    @GetMapping("/usuario/{usuarioId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> obtenerReservasPorUsuario(@PathVariable Long usuarioId) {
        reservaService.listarReservasPorUsuario(usuarioId);
        return ResponseEntity.ok("Reservas del usuario obtenidas correctamente.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarReserva(@PathVariable Long id, @RequestBody Reserva reserva, Authentication authentication) {
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
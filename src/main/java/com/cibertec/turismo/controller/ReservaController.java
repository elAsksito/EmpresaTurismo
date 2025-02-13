package com.cibertec.turismo.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.turismo.model.Reserva;
import com.cibertec.turismo.service.interfaces.IReservaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/reservas")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public class ReservaController {
	
	private final IReservaService reservaService;

    @PostMapping
    public ResponseEntity<Reserva> crearReserva(@RequestBody Reserva reserva) {
        return ResponseEntity.ok(reservaService.crearReserva(reserva));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Reserva>> listarReservasPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(reservaService.listarReservasPorUsuario(usuarioId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reserva> obtenerReservaPorId(@PathVariable Long id) {
        return reservaService.obtenerReservaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Reserva> actualizarEstadoReserva(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String estado = body.get("estado");
        
        List<String> estadosValidos = Arrays.asList("PENDIENTE", "CONFIRMADA", "CANCELADA", "COMPLETADA");
        if (!estadosValidos.contains(estado.toUpperCase())) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(reservaService.actualizarEstadoReserva(id, estado.toUpperCase()));
    }
}
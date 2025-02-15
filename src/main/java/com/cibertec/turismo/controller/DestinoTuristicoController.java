package com.cibertec.turismo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> crearDestino(@RequestBody DestinoTuristico destino) {
        destinoService.crearDestino(destino);
        return ResponseEntity.ok("Destino creado correctamente.");
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
    public ResponseEntity<String> actualizarDestino(
            @PathVariable Long id,
            @RequestBody DestinoTuristico destino) {
        destinoService.actualizarDestino(id, destino);
        return ResponseEntity.ok("Destino actualizado correctamente.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarDestino(@PathVariable Long id) {
        boolean eliminado = destinoService.eliminarDestino(id);
        if (!eliminado) {
            return ResponseEntity.badRequest().body("No se puede eliminar el destino porque tiene reservas asociadas.");
        }
        return ResponseEntity.ok("Destino eliminado correctamente.");
    }

    @GetMapping("/buscar")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> buscarPorNombre(@RequestParam String nombre) {
        List<DestinoTuristico> resultados = destinoService.buscarPorNombre(nombre);
        if (resultados.isEmpty()) {
            return ResponseEntity.badRequest().body("No se encontraron destinos con ese nombre.");
        }
        return ResponseEntity.ok(resultados);
    }
}

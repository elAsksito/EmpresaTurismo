package com.cibertec.turismo.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cibertec.turismo.model.DestinoTuristico;
import com.cibertec.turismo.model.Reserva;
import com.cibertec.turismo.model.Usuario;
import com.cibertec.turismo.repository.DestinoTuristicoRepository;
import com.cibertec.turismo.repository.ReservaRepository;
import com.cibertec.turismo.repository.UsuarioRepository;
import com.cibertec.turismo.service.interfaces.IReservaService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservaServiceImpl implements IReservaService {

    private final ReservaRepository reservaRepository;
    private final UsuarioRepository usuarioRepository;
    private final DestinoTuristicoRepository destinoRepository;

    @Override
    public Reserva crearReserva(Reserva reserva) {
        if (!usuarioRepository.existsById(reserva.getUsuario().getId())) {
            throw new IllegalArgumentException("Usuario no encontrado.");
        }
        if (!destinoRepository.existsById(reserva.getDestino().getId())) {
            throw new IllegalArgumentException("Destino turístico no encontrado.");
        }

        Usuario usuario = usuarioRepository.findById(reserva.getUsuario().getId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        DestinoTuristico destino = destinoRepository.findById(reserva.getDestino().getId())
                .orElseThrow(() -> new IllegalArgumentException("Destino turístico no encontrado"));

        reserva.setUsuario(usuario);
        reserva.setDestino(destino);
        return reservaRepository.save(reserva);
    }

    @Override
    public List<Reserva> listarReservas() {
        return reservaRepository.findAll();
    }

    @Override
    public List<Reserva> listarReservasPorUsuario(Long usuarioId) {
        return reservaRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public Optional<Reserva> obtenerReservaPorId(Long id) {
        return reservaRepository.findById(id);
    }

    @Override
    public Reserva actualizarReserva(Long id, Reserva reservaActualizada) {
        return reservaRepository.findById(id).map(reserva -> {
            if (reservaActualizada.getFechaReserva() != null) {
                reserva.setFechaReserva(reservaActualizada.getFechaReserva());
            }
            if (reservaActualizada.getEstado() != null && !reservaActualizada.getEstado().isEmpty()) {
                reserva.setEstado(reservaActualizada.getEstado());
            }
            if (reservaActualizada.getUsuario() != null) {
                Usuario usuario = usuarioRepository.findById(reservaActualizada.getUsuario().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));
                reserva.setUsuario(usuario);
            }
            if (reservaActualizada.getDestino() != null) {
                DestinoTuristico destino = destinoRepository.findById(reservaActualizada.getDestino().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Destino turístico no encontrado."));
                reserva.setDestino(destino);
            }
            return reservaRepository.save(reserva);
        }).orElseThrow(() -> new RuntimeException("Reserva no encontrada."));
    }

    @Override
    public void eliminarReserva(Long id) {
        if (!reservaRepository.existsById(id)) {
            throw new RuntimeException("La reserva no existe.");
        }
        reservaRepository.deleteById(id);
    }
}

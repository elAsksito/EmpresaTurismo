package com.cibertec.turismo.service.interfaces;

import java.util.List;
import java.util.Optional;

import com.cibertec.turismo.model.Reserva;

public interface IReservaService {
	Reserva crearReserva(Reserva reserva);
    List<Reserva> listarReservasPorUsuario(Long usuarioId);
    Optional<Reserva> obtenerReservaPorId(Long id);
    Reserva actualizarEstadoReserva(Long id, String nuevoEstado);
}
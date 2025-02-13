package com.cibertec.turismo.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
public class ReservaServiceImpl implements IReservaService{

	@Autowired
	private ReservaRepository reservaRepository;
	
	@Autowired
    private UsuarioRepository usuarioRepository;
	
	@Autowired
    private DestinoTuristicoRepository destinoRepository;

    @Override
    public Reserva crearReserva(Reserva reserva) {
        if (!usuarioRepository.existsById(reserva.getUsuario().getId())) {
            throw new RuntimeException("Usuario no encontrado.");
        }
        if (!destinoRepository.existsById(reserva.getDestino().getId())) {
            throw new RuntimeException("Destino turístico no encontrado.");
        }
        
        Usuario usuario = usuarioRepository.findById(reserva.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        DestinoTuristico destino = destinoRepository.findById(reserva.getDestino().getId())
                .orElseThrow(() -> new RuntimeException("Destino turístico no encontrado"));

        reserva.setUsuario(usuario);
        reserva.setDestino(destino);
        return reservaRepository.save(reserva);
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
    public Reserva actualizarEstadoReserva(Long id, String nuevoEstado) {
        return reservaRepository.findById(id)
            .map(reserva -> {
                reserva.setEstado(nuevoEstado);
                return reservaRepository.save(reserva);
            })
            .orElseThrow(() -> new RuntimeException("Reserva no encontrada."));
    }

}

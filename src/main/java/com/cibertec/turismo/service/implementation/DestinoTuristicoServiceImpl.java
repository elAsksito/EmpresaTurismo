package com.cibertec.turismo.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cibertec.turismo.model.DestinoTuristico;
import com.cibertec.turismo.repository.DestinoTuristicoRepository;
import com.cibertec.turismo.repository.ReservaRepository;
import com.cibertec.turismo.service.interfaces.IDestinoTuristicoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DestinoTuristicoServiceImpl implements IDestinoTuristicoService {

    private final DestinoTuristicoRepository destinoRepository;
    private final ReservaRepository reservaRepository;

    @Override
    public DestinoTuristico crearDestino(DestinoTuristico destino) {
        return destinoRepository.save(destino);
    }

    @Override
    public List<DestinoTuristico> listarDestinos() {
        return destinoRepository.findAll();
    }

    @Override
    public Optional<DestinoTuristico> obtenerDestinoPorId(Long id) {
        return destinoRepository.findById(id);
    }

    @Override
    public DestinoTuristico actualizarDestino(Long id, DestinoTuristico destino) {
        return destinoRepository.findById(id).map(d -> {
            d.setNombre(destino.getNombre());
            d.setDescripcion(destino.getDescripcion());
            d.setUbicacion(destino.getUbicacion());
            return destinoRepository.save(d);
        }).orElseThrow(() -> new RuntimeException("Destino no encontrado"));
    }

    @Override
    public boolean eliminarDestino(Long id) {
        if (!reservaRepository.findByDestinoId(id).isEmpty()) {
            return false;
        }
        destinoRepository.deleteById(id);
        return true;
    }

    @Override
    public List<DestinoTuristico> buscarPorNombre(String nombre) {
        return destinoRepository.findByNombreContainingIgnoreCase(nombre);
    }
}

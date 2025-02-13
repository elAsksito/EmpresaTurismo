package com.cibertec.turismo.service.interfaces;

import java.util.List;
import java.util.Optional;

import com.cibertec.turismo.model.DestinoTuristico;

public interface IDestinoTuristicoService {
	DestinoTuristico crearDestino(DestinoTuristico destino);
    List<DestinoTuristico> listarDestinos();
    Optional<DestinoTuristico> obtenerDestinoPorId(Long id);
}
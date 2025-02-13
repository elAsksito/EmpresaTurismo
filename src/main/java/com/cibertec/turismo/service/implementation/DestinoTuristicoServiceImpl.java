package com.cibertec.turismo.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.turismo.model.DestinoTuristico;
import com.cibertec.turismo.repository.DestinoTuristicoRepository;
import com.cibertec.turismo.service.interfaces.IDestinoTuristicoService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class DestinoTuristicoServiceImpl implements IDestinoTuristicoService{

	@Autowired
	private DestinoTuristicoRepository destinoRepository;
	
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

}

package com.cibertec.turismo.service.implementation;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cibertec.turismo.model.DestinoTuristico;
import com.cibertec.turismo.model.Reserva;
import com.cibertec.turismo.model.Usuario;
import com.cibertec.turismo.repository.DestinoTuristicoRepository;
import com.cibertec.turismo.repository.ReservaRepository;
import com.cibertec.turismo.repository.UsuarioRepository;
import com.cibertec.turismo.service.interfaces.IExportImportService;
import com.cibertec.turismo.soap.models.list.ListaDestinos;
import com.cibertec.turismo.soap.models.list.ListaReservas;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExportImportServiceImpl implements IExportImportService{
	
	private final DestinoTuristicoRepository destinoRepo;
	private final ReservaRepository reservaRepo;
	private final UsuarioRepository usuarioRepo;
	private final ObjectMapper objectMapper;
	private final XmlMapper xmlMapper = new XmlMapper();
	
	@Override
	public void exportJson(String filePath, String entidad) {
		try {
			File file = new File(filePath + "/" + entidad.toLowerCase() + ".json");
		
			switch (entidad.toLowerCase()) {
				case "reservas":
					List<Reserva> reservas = reservaRepo.findAll();
					objectMapper.writeValue(file, reservas);
					break;
				case "destinos":
					List<DestinoTuristico> destinos  = destinoRepo.findAll();
					objectMapper.writeValue(file, destinos );
					break;
				case "usuarios":
					List<Usuario> usuarios = usuarioRepo.findAll();
					objectMapper.writeValue(file, usuarios);
					break;
				default:
					return;
			}
		
		} catch (IOException e) {
			
		}
	}

	@Override
	public void importJson(String filePath, String entidad) {
		try {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new RuntimeException("Archivo no encontrado: " + filePath);
            }

            if (entidad.equalsIgnoreCase("reservas")) {
                List<Reserva> reservas = List.of(objectMapper.readValue(file, Reserva[].class));
                reservaRepo.saveAll(reservas);
            } else if (entidad.equalsIgnoreCase("destinos")) {
                List<DestinoTuristico> destinos = List.of(objectMapper.readValue(file, DestinoTuristico[].class));
                destinoRepo.saveAll(destinos);
            } else {
                throw new IllegalArgumentException("Entidad no válida para importar.");
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al importar JSON", e);
        }
	}

	@Override
	public void exportXML(String filePath, String entidad) {
		try {
			File file = new File(filePath + "/" + entidad.toLowerCase() + ".xml");
			JAXBContext context;
			Marshaller marshaller;

			switch (entidad.toLowerCase()) {
				case "reservas":
					List<Reserva> reservas = reservaRepo.findAll();
	                ListaReservas reservaList = new ListaReservas(reservas);
	                context = JAXBContext.newInstance(ListaReservas.class);
	                marshaller = context.createMarshaller();
	                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	                marshaller.marshal(reservaList, file);
					break;
				case "destinos":
					List<DestinoTuristico> destinos = destinoRepo.findAll();
	                ListaDestinos destinoList = new ListaDestinos(destinos);
	                context = JAXBContext.newInstance(ListaDestinos.class);
	                marshaller = context.createMarshaller();
	                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	                marshaller.marshal(destinoList, file);
					break;
				default:
					return;
			}
		} catch (JAXBException e) {
			
		}
	}

	@Override
	public void importXML(String filePath, String entidad) {
		try {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new RuntimeException("Archivo no encontrado: " + filePath);
            }

            if (entidad.equalsIgnoreCase("reservas")) {
                List<Reserva> reservas = List.of(xmlMapper.readValue(file, Reserva[].class));
                reservaRepo.saveAll(reservas);
            } else if (entidad.equalsIgnoreCase("destinos")) {
                List<DestinoTuristico> destinos = List.of(xmlMapper.readValue(file, DestinoTuristico[].class));
                destinoRepo.saveAll(destinos);
            } else {
                throw new IllegalArgumentException("Entidad no válida para importar.");
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al importar XML", e);
        }
	}
	

}
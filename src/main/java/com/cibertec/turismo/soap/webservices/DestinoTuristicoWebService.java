package com.cibertec.turismo.soap.webservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cibertec.turismo.model.DestinoTuristico;
import com.cibertec.turismo.service.interfaces.IDestinoTuristicoService;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

import java.util.List;
import java.util.Optional;

@WebService(serviceName = "DestinoTuristicoService")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
@Component
public class DestinoTuristicoWebService {

	@Autowired
    private IDestinoTuristicoService destinoTuristicoService;

    @WebMethod
    public DestinoTuristico crearDestino(DestinoTuristico destino) {
        return destinoTuristicoService.crearDestino(destino);
    }

    @WebMethod
    public List<DestinoTuristico> listarDestinos() {
        return destinoTuristicoService.listarDestinos();
    }

    @WebMethod
    public DestinoTuristico obtenerDestinoPorId(Long id) {
        Optional<DestinoTuristico> destino = destinoTuristicoService.obtenerDestinoPorId(id);
        return destino.orElse(null);
    }
}

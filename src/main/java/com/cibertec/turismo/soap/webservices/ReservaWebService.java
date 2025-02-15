package com.cibertec.turismo.soap.webservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cibertec.turismo.model.Reserva;
import com.cibertec.turismo.service.interfaces.IReservaService;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import java.util.List;
import java.util.Optional;

@WebService(serviceName = "ReservaService")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
@Component
public class ReservaWebService {

    @Autowired
    private IReservaService reservaService;

    @WebMethod
    public Reserva crearReserva(Reserva reserva) {
        return reservaService.crearReserva(reserva);
    }

    @WebMethod
    public List<Reserva> listarReservasPorUsuario(Long usuarioId) {
        return reservaService.listarReservasPorUsuario(usuarioId);
    }

    @WebMethod
    public Reserva obtenerReservaPorId(Long id) {
        Optional<Reserva> reserva = reservaService.obtenerReservaPorId(id);
        return reserva.orElse(null);
    }

    @WebMethod
    public Reserva actualizarEstadoReserva(Long id, Reserva reserva) {
        return reservaService.actualizarReserva(id, reserva);
    }
}
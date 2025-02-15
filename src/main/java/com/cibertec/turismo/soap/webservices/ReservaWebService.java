package com.cibertec.turismo.soap.webservices;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import com.cibertec.turismo.model.Reserva;
import com.cibertec.turismo.model.Usuario;
import com.cibertec.turismo.service.interfaces.IReservaService;
import com.cibertec.turismo.service.interfaces.IUsuarioService;
import com.cibertec.turismo.soap.models.list.ListaReservas;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@WebService(serviceName = "ReservaService")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
@Component
@RequiredArgsConstructor
public class ReservaWebService {

    private final IReservaService reservaService;
    private final IUsuarioService usuarioService;
    private final Validator validator;

    @WebMethod
    public String crearReserva(Reserva reserva) {
    	String errores = validar(reserva);
        if (errores != null) {
            return errores;
        }
        try {
            reservaService.crearReserva(reserva);
            return "Reserva creada exitosamente.";
        } catch (Exception e) {
            return "Error al crear reserva: " + e.getMessage();
        }
    }

    @WebMethod
    public ListaReservas listarReservas() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            throw new RuntimeException("No estás autenticado.");
        }

        String emailUsuario = authentication.getName();
        Usuario usuario = usuarioService.obtenerUsuarioPorEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<Reserva> reservas;

        if (usuario.getRole().equalsIgnoreCase("ADMIN")) {
            reservas = reservaService.listarReservas();
        } else {
            reservas = reservaService.listarReservasPorUsuario(usuario.getId());
        }

        return new ListaReservas(reservas);
    }


    @WebMethod
    public String obtenerReservaPorId(Long id) {
        Optional<Reserva> reserva = reservaService.obtenerReservaPorId(id);
        return reserva.map(Object::toString).orElse("Reserva no encontrada.");
    }

    @WebMethod
    public String actualizarEstadoReserva(Long id, Reserva reserva) {
        if (!esAdmin()) {
            return "Acceso denegado: solo los administradores pueden actualizar reservas.";
        }
        
        String errores = validar(reserva);
        if (errores != null) {
            return errores;
        }
        
        try {
            reservaService.actualizarReserva(id, reserva);
            return "Reserva actualizada correctamente.";
        } catch (Exception e) {
            return "Error al actualizar reserva: " + e.getMessage();
        }
    }

    @WebMethod
    public String eliminarReserva(Long id) {
        if (!esAdmin()) {
            return "Acceso denegado: solo los administradores pueden eliminar reservas.";
        }
        
        try {
            reservaService.eliminarReserva(id);
            return "Reserva eliminada correctamente.";
        } catch (Exception e) {
            return "Error al eliminar reserva: " + e.getMessage();
        }
    }

    private boolean esAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
    }
    
    private String validar(Reserva reserva) {
    	Set<ConstraintViolation<Reserva>> errores = validator.validate(reserva);

		if (!errores.isEmpty()) {
			StringBuilder mensajeErrores = new StringBuilder("Errores de validación: ");
			for (ConstraintViolation<Reserva> error : errores) {
				mensajeErrores.append(error.getPropertyPath()).append(": ").append(error.getMessage()).append("; ");
			}
			return mensajeErrores.toString();
		}
		return null;
    }
}
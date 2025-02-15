package com.cibertec.turismo.soap.webservices;

import com.cibertec.turismo.model.DestinoTuristico;
import com.cibertec.turismo.service.interfaces.IDestinoTuristicoService;
import com.cibertec.turismo.soap.models.list.ListaDestinos;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@WebService(serviceName = "DestinoTuristicoService")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
@Component
@RequiredArgsConstructor
public class DestinoTuristicoWebService {

    private final IDestinoTuristicoService destinoTuristicoService;
    private final Validator validator;

    @WebMethod
    public String crearDestino(DestinoTuristico destino) {
        if (!esAdmin()) {
            return "Acceso denegado: solo los administradores pueden crear destinos.";
        }
        
        String errores = validar(destino);
        if (errores != null) {
            return errores;
        }

        try {
            destinoTuristicoService.crearDestino(destino);
            return "Destino creado correctamente.";
        } catch (RuntimeException e) {
            return "Error al crear destino: " + e.getMessage();
        }
    }

    @WebMethod
    public ListaDestinos listarDestinos() {
        List<DestinoTuristico> destinos = destinoTuristicoService.listarDestinos();
        return new ListaDestinos(destinos);
    }

    @WebMethod
    public DestinoTuristico obtenerDestinoPorId(Long id) {
        return destinoTuristicoService.obtenerDestinoPorId(id).orElse(null);
    }

    @WebMethod
    public String actualizarDestino(Long id, DestinoTuristico destino) {
        if (!esAdmin()) {
            return "Acceso denegado: solo los administradores pueden actualizar destinos.";
        }
        
        String errores = validar(destino);
        if (errores != null) {
            return errores;
        }

        try {
            destinoTuristicoService.actualizarDestino(id, destino);
            return "Destino actualizado correctamente.";
        } catch (RuntimeException e) {
            return "Error al actualizar destino: " + e.getMessage();
        }
    }

    @WebMethod
    public String eliminarDestino(Long id) {
        if (!esAdmin()) {
            return "Acceso denegado: solo los administradores pueden eliminar destinos.";
        }

        try {
            boolean eliminado = destinoTuristicoService.eliminarDestino(id);
            return eliminado ? "Destino eliminado correctamente." :
                    "No se puede eliminar el destino porque tiene reservas asociadas.";
        } catch (RuntimeException e) {
            return "Error al eliminar destino: " + e.getMessage();
        }
    }

    @WebMethod
    public ListaDestinos buscarPorNombre(String nombre) {
        List<DestinoTuristico> resultados = destinoTuristicoService.buscarPorNombre(nombre);
        return new ListaDestinos(resultados);
    }

    private boolean esAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
    }
    
    private String validar(DestinoTuristico destino) {
    	Set<ConstraintViolation<DestinoTuristico>> errores = validator.validate(destino);

		if (!errores.isEmpty()) {
			StringBuilder mensajeErrores = new StringBuilder("Errores de validación: ");
			for (ConstraintViolation<DestinoTuristico> error : errores) {
				mensajeErrores.append(error.getPropertyPath()).append(": ").append(error.getMessage()).append("; ");
			}
			return mensajeErrores.toString();
		}
		return null;
    }
    
}

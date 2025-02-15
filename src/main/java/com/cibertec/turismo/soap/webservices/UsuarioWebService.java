package com.cibertec.turismo.soap.webservices;

import com.cibertec.turismo.model.Usuario;
import com.cibertec.turismo.service.interfaces.IUsuarioService;
import com.cibertec.turismo.soap.models.list.ListaUsuarios;

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

@WebService(serviceName = "UsuarioService")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
@Component
@RequiredArgsConstructor
public class UsuarioWebService {

    private final IUsuarioService usuarioService;
    private final Validator validator;

    @WebMethod
    public String registrarUsuario(Usuario usuario) {
    	String errores = validar(usuario);
        if (errores != null) {
            return errores;
        }
        
        try {
            usuarioService.registrarUsuario(usuario);
            return "Usuario registrado correctamente.";
        } catch (RuntimeException e) {
            return "Error al registrar usuario: " + e.getMessage();
        }
    }

    @WebMethod
    public Usuario obtenerUsuarioPorId(Long id) {
    	if (!esAdmin()) {
            return null;
        }
        return usuarioService.obtenerUsuarioPorId(id).orElse(null);
    }

    @WebMethod
    public Usuario obtenerUsuarioPorEmail(String email) {
    	if (!esAdmin()) {
            return null;
        }
        return usuarioService.obtenerUsuarioPorEmail(email).orElse(null);
    }

    @WebMethod
    public ListaUsuarios listarUsuarios() {
    	if (!esAdmin()) {
            return null;
        }
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        return new ListaUsuarios(usuarios);
    }

    @WebMethod
    public String actualizarUsuario(Long id, Usuario usuario) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            return "No estás autenticado.";
        }

        String emailSolicitante = authentication.getName();
        boolean esAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        String errores = validar(usuario);
        if (errores != null) {
            return errores;
        }
        
        try {
            usuarioService.actualizarUsuario(id, usuario, emailSolicitante, esAdmin);
            return "Usuario actualizado correctamente.";
        } catch (RuntimeException e) {
            return "Error al actualizar usuario: " + e.getMessage();
        }
    }

    @WebMethod
    public String eliminarUsuario(Long id) {
    	if (!esAdmin()) {
            return "Acceso denegado: solo los administradores pueden actualizar usuarios.";
        }
        try {
            usuarioService.eliminarUsuario(id);
            return "Usuario eliminado correctamente.";
        } catch (RuntimeException e) {
            return "Error al eliminar usuario: " + e.getMessage();
        }
    }
    
    private boolean esAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
    }
    
    private String validar(Usuario usuario) {
    	Set<ConstraintViolation<Usuario>> errores = validator.validate(usuario);

		if (!errores.isEmpty()) {
			StringBuilder mensajeErrores = new StringBuilder("Errores de validación: ");
			for (ConstraintViolation<Usuario> error : errores) {
				mensajeErrores.append(error.getPropertyPath()).append(": ").append(error.getMessage()).append("; ");
			}
			return mensajeErrores.toString();
		}
		return null;
    }
}
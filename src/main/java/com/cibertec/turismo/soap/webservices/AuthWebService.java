package com.cibertec.turismo.soap.webservices;

import java.util.Set;

import org.springframework.stereotype.Component;

import com.cibertec.turismo.exception.WebServiceException;
import com.cibertec.turismo.model.Usuario;
import com.cibertec.turismo.service.interfaces.IAuthService;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

@WebService(serviceName = "AuthService")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
@Component
@RequiredArgsConstructor
public class AuthWebService {

	private final IAuthService authService;
	private final Validator validator;

	@WebMethod
	public String login(String email, String password) {
		return authService.login(email, password);
	}

	@WebMethod
	public String registrar(Usuario usuario) {
		Set<ConstraintViolation<Usuario>> errores = validator.validate(usuario);

		if (!errores.isEmpty()) {
			StringBuilder mensajeErrores = new StringBuilder("Errores de validación: ");
			for (ConstraintViolation<Usuario> error : errores) {
				mensajeErrores.append(error.getPropertyPath()).append(": ").append(error.getMessage()).append("; ");
			}
			throw new WebServiceException(mensajeErrores.toString(), "400");
		}

		try {
			authService.registrar(usuario);
			return "Usuario creado correctamente.";
		} catch (RuntimeException e) {
			return "Error al registrar usuario: " + e.getMessage();
		}
	}
}
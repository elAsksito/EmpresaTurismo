package com.cibertec.turismo.soap.webservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cibertec.turismo.model.Usuario;
import com.cibertec.turismo.service.interfaces.IAuthService;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

@WebService(serviceName = "AuthService")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
@Component
public class AuthWebService {
	
	@Autowired
    private IAuthService authService;

    @WebMethod
    public String login(String email, String password) {
        return authService.login(email, password);
    }

    @WebMethod
    public Usuario registrar(Usuario usuario) {
        return authService.registrar(usuario);
    }
}
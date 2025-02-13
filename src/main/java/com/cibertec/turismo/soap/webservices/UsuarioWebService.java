package com.cibertec.turismo.soap.webservices;

import com.cibertec.turismo.model.Usuario;
import com.cibertec.turismo.service.interfaces.IUsuarioService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@WebService(serviceName = "UsuarioService")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
@Component
public class UsuarioWebService {

	@Autowired
    private IUsuarioService usuarioService;

    @WebMethod
    public Usuario registrarUsuario(Usuario usuario) {
        return usuarioService.registrarUsuario(usuario);
    }

    @WebMethod
    public Usuario obtenerUsuarioPorId(Long id) {
        Optional<Usuario> usuario = usuarioService.obtenerUsuarioPorId(id);
        return usuario.orElse(null); // Devuelve null si no existe
    }

    @WebMethod
    public Usuario obtenerUsuarioPorEmail(String email) {
        Optional<Usuario> usuario = usuarioService.obtenerUsuarioPorEmail(email);
        return usuario.orElse(null);
    }
}
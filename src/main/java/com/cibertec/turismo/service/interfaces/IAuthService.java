package com.cibertec.turismo.service.interfaces;

import com.cibertec.turismo.model.Usuario;

public interface IAuthService {
	String login(String email, String password);
    Usuario registrar(Usuario usuario);
}
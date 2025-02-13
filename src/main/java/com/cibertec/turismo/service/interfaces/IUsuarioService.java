package com.cibertec.turismo.service.interfaces;

import java.util.Optional;

import com.cibertec.turismo.model.Usuario;

public interface IUsuarioService {
	Usuario registrarUsuario(Usuario usuario);
    Optional<Usuario> obtenerUsuarioPorId(Long id);
    Optional<Usuario> obtenerUsuarioPorEmail(String email);
}
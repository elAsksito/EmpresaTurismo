package com.cibertec.turismo.service.interfaces;

import java.util.List;
import java.util.Optional;

import com.cibertec.turismo.model.Usuario;

public interface IUsuarioService {
	Usuario registrarUsuario(Usuario usuario);
    Optional<Usuario> obtenerUsuarioPorId(Long id);
    Optional<Usuario> obtenerUsuarioPorEmail(String email);
    List<Usuario> listarUsuarios();
    Usuario actualizarUsuario(Long id, Usuario usuario, String emailSolicitante, boolean esAdmin);
    void eliminarUsuario(Long id);
}
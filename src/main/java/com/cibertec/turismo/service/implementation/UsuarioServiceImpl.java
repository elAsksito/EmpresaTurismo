package com.cibertec.turismo.service.implementation;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cibertec.turismo.model.Usuario;
import com.cibertec.turismo.repository.UsuarioRepository;
import com.cibertec.turismo.service.interfaces.IUsuarioService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements IUsuarioService{
	
	private final UsuarioRepository usuarioRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public Usuario registrarUsuario(Usuario usuario) {
		if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("El correo ya está registrado.");
        }
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
	}

	@Override
	public Optional<Usuario> obtenerUsuarioPorId(Long id) {
		return usuarioRepository.findById(id);
	}

	@Override
	public Optional<Usuario> obtenerUsuarioPorEmail(String email) {
		return usuarioRepository.findByEmail(email);
	}

}
package com.cibertec.turismo.service.implementation;

import java.util.List;
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

	@Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

	@Override
    public Usuario actualizarUsuario(Long id, Usuario usuario, String emailSolicitante, boolean esAdmin) {
        return usuarioRepository.findById(id).map(existingUser -> {
            if (esAdmin || existingUser.getEmail().equals(emailSolicitante)) {
                existingUser.setEmail(usuario.getEmail());
                if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
                    existingUser.setPassword(passwordEncoder.encode(usuario.getPassword()));
                }
                if (esAdmin) {
                    existingUser.setRole(usuario.getRole());
                }
                return usuarioRepository.save(existingUser);
            } else {
                throw new RuntimeException("No tienes permisos para actualizar este usuario.");
            }
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado."));
    }

    @Override
    public void eliminarUsuario(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
        } else {
            throw new RuntimeException("Usuario no encontrado.");
        }
    }
}
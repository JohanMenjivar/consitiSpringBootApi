package dev.jmenjivar.crud2.security.service;

import dev.jmenjivar.crud2.security.entity.Usuario;
import dev.jmenjivar.crud2.security.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UsuarioService {

	@Autowired
	
	UsuarioRepository usuarioRepository;
	public Optional<Usuario> getByNombreUsuario(String nombreUsuario){
		return usuarioRepository.findByNombreUsuario(nombreUsuario);
	}
	
	public boolean existsByNombreUsuario(String nombreUsuario) {
		return usuarioRepository.existsByEmail(nombreUsuario);
	}
	
	public boolean existsByEmail(String email) {
		return usuarioRepository.existsByEmail(email);
	}
	
	public void save (Usuario usuario) {
		usuarioRepository.save(usuario);
	}
	
}

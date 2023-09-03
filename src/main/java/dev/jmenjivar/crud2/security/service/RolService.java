package dev.jmenjivar.crud2.security.service;

import dev.jmenjivar.crud2.security.entity.Rol;
import dev.jmenjivar.crud2.security.enums.RolNombre;
import dev.jmenjivar.crud2.security.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class RolService {
	
	@Autowired
	RolRepository rolRepository;
	
	public Optional<Rol> getByRolNombre(RolNombre rolNombre){
		return rolRepository.findByRolNombre(rolNombre);
	}
	public void save(Rol rol) {
		rolRepository.save(rol);
	}
	
}

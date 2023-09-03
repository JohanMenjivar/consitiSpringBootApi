package dev.jmenjivar.crud2.security.repository;

import dev.jmenjivar.crud2.security.entity.Rol;
import dev.jmenjivar.crud2.security.enums.RolNombre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
public interface RolRepository extends JpaRepository<Rol,Integer> {
	Optional<Rol> findByRolNombre(RolNombre rolNombre);
}

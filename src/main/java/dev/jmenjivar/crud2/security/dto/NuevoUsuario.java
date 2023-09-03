package dev.jmenjivar.crud2.security.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class NuevoUsuario {
	
	@NotBlank
	private String nombre;
	@NotBlank
	private String nombreUsuario;
	@NotBlank
	private String email;
	@NotBlank
	private String password;
	private Set<String> roles = new HashSet<>();
}

package dev.jmenjivar.crud2.security.controller;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.jmenjivar.crud2.dto.Mensaje;
import dev.jmenjivar.crud2.security.dto.JwtDto;
import dev.jmenjivar.crud2.security.dto.NuevoUsuario;
import dev.jmenjivar.crud2.security.dto.LoginUsuario;
import dev.jmenjivar.crud2.security.entity.Rol;
import dev.jmenjivar.crud2.security.entity.Usuario;
import dev.jmenjivar.crud2.security.enums.RolNombre;
import dev.jmenjivar.crud2.security.jwt.JwtProvider;
import dev.jmenjivar.crud2.security.service.RolService;
import dev.jmenjivar.crud2.security.service.UsuarioService;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	RolService rolService;
	
	@Autowired
	JwtProvider jwtProvider;
	
	@PostMapping("")
	public ResponseEntity<Mensaje> nuevo(@Valid @RequestBody NuevoUsuario nuevoUsuario, BindingResult bindingResult){
		if(bindingResult.hasErrors())
			return new ResponseEntity<Mensaje>(new Mensaje("Verifique los dartos introducidos"), HttpStatus.BAD_REQUEST);
		if(usuarioService.existsByNombreUsuario(nuevoUsuario.getNombreUsuario()))
			return new ResponseEntity<Mensaje>(new Mensaje("El nombre " + nuevoUsuario.getNombre() + " ya esta registrado"), HttpStatus.BAD_REQUEST);
		if(usuarioService.existsByEmail(nuevoUsuario.getEmail()))
			return new ResponseEntity<Mensaje>(new Mensaje("El email " + nuevoUsuario.getEmail() + " ya esta registrado"), HttpStatus.BAD_REQUEST);
		Usuario usuario = new Usuario(nuevoUsuario.getNombre(), nuevoUsuario.getNombreUsuario(), nuevoUsuario.getEmail(), 
							passwordEncoder.encode(nuevoUsuario.getPassword()));
		Set <Rol> roles = new HashSet<>();
		roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());
		if(nuevoUsuario.getRoles().contains("admin"))
			roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
		usuario.setRoles(roles);
		usuarioService.save(usuario);
		return new ResponseEntity<Mensaje>(new Mensaje("Registrado con exito"), HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login (@Valid @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult){
		if(bindingResult.hasErrors())
			return new ResponseEntity<Mensaje>(new Mensaje("Usuario invalido"),HttpStatus.UNAUTHORIZED);
		Authentication authentication = 
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUsuario.getNombreUsuario(),loginUsuario.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtProvider.generateToken(authentication);
		JwtDto jwtDto = new JwtDto(jwt);
		return new ResponseEntity<JwtDto>(jwtDto, HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/refresh")
	public ResponseEntity<JwtDto> refresh(@RequestBody JwtDto jwtDto) throws ParseException{
		String token = jwtProvider.refreshToken(jwtDto);
		JwtDto jwt = new JwtDto(token);
		return new ResponseEntity<JwtDto>(jwt, HttpStatus.OK);
	}
	
	
}

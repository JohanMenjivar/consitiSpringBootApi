package dev.jmenjivar.crud2.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class JwtDto {
	private String token;
	
	public JwtDto(String token) {
		this.token = token;
	}
}

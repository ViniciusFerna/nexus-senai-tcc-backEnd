package com.nexus.nexus.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class loginDto {

	@NotNull(message = "O Email é obrigatório")
	@Email(message = "O Email deve ser válido")
	private String email;
	
	@NotNull(message = "A senha é obrigatória")
	private String senha;
	
}

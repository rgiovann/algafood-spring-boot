package com.algaworks.algafood.api.input;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioInput {
	
	@NotBlank
	private String nome;
	
	@NotBlank
	@Email
	private String email;

	@NotBlank
	private String senha;

}

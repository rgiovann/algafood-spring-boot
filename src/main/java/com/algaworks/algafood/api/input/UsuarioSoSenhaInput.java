package com.algaworks.algafood.api.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioSoSenhaInput {
	
	@NotBlank
	private String senhaAtual;
	
	@NotBlank
	private String novaSenha;

}

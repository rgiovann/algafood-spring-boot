package com.algaworks.algafood.api.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CozinhaNomeInput {
	
	@NotBlank
	private String nome;

}

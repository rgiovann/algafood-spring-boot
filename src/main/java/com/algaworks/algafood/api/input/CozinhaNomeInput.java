package com.algaworks.algafood.api.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CozinhaNomeInput {
	
	@NotBlank
	@ApiModelProperty(value="Nome da cozinha",example="Brasileira",required=true)  // criar plugin "not blank"
	private String nome;

}

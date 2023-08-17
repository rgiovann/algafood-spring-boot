package com.algaworks.algafood.api.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GrupoNomeInput {
	
	@NotBlank
	@ApiModelProperty(value="Nome do grupo de usuário",example="Gerente",required=true)  // criar plugin "not blank"
	private String nome;

}

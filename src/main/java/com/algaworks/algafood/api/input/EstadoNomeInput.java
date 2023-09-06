package com.algaworks.algafood.api.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EstadoNomeInput {
	
	@ApiModelProperty(value="Nome do estado",example="Santa Catarina")
	@NotBlank
	private String nome;

}

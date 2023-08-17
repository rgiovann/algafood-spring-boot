package com.algaworks.algafood.api.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class CidadeInput {
	
	@NotBlank
	@ApiModelProperty(value="Nome da cidade.",example="Blumenau",required=true)  // criar plugin "not blank"
	private String nome;
	
	@Valid
	@NotNull
	@ApiModelProperty(value="Id do estado.",example="1")
	private EstadoIdInput estado;

}

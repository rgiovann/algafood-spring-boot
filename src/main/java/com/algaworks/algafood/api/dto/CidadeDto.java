package com.algaworks.algafood.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "Cidade", description = "Representa uma cidade.")
@Setter
@Getter
public class CidadeDto {
	
	@ApiModelProperty(value="Id da cidade.",example="1")
	private Long id;
	
	@ApiModelProperty(value="Nome da cidade.",example="Blumenau")
	private String nome;
 
	EstadoDto estado;

}

package com.algaworks.algafood.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "Restaurante", description = "Representa os dados de um restaurante")
public class RestauranteCompactDto {
	
	@ApiModelProperty(value="Id do restaurante",example="1")
	private Long id;
	
	@ApiModelProperty(value="Nome do restaurante",example="Restaurante Serra Azul")	
	private String nome;

}

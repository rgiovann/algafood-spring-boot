package com.algaworks.algafood.api.openapi.model;

import java.math.BigDecimal;

import com.algaworks.algafood.api.dto.CozinhaDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("RestauranteBasicoDto")
public class RestauranteBasicoDtoOpenApi {
	
	@ApiModelProperty(value="Id do restaurante",example="1")
	private Long id;
	@ApiModelProperty(value="Nome do restaurante",example="Restaurante Delicia Na Brasa")
	private String nome;
	@ApiModelProperty(value="Valor do frete",example="10.55")
	private BigDecimal taxaFrete;
	private CozinhaDto cozinha;


}

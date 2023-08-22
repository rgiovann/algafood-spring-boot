package com.algaworks.algafood.api.dto;

import com.algaworks.algafood.api.dto.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class CozinhaDto {

	@JsonView(RestauranteView.Resumo.class)
	@ApiModelProperty(value="Id da cozinha",example="1")
	private Long id;
	
	@JsonView(RestauranteView.Resumo.class)	
	@ApiModelProperty(value="Nome da cozinha",example="Brasileira")
	private String nome;

}
package com.algaworks.algafood.api.dto;

import org.springframework.hateoas.RepresentationModel;

import com.algaworks.algafood.api.dto.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@ApiModel(value = "Cozinha", description = "Representa uma cozinha")

public class CozinhaDto extends RepresentationModel<CozinhaDto> implements DtoInterface<CozinhaDto>{

	@JsonView(RestauranteView.Resumo.class)
	@ApiModelProperty(value="Id da cozinha",example="1")
	private Long id;
	
	@JsonView(RestauranteView.Resumo.class)	
	@ApiModelProperty(value="Nome da cozinha",example="Brasileira")
	private String nome;

}
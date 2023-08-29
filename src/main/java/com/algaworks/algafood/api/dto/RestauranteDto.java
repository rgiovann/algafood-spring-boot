package com.algaworks.algafood.api.dto;

import java.math.BigDecimal;

import com.algaworks.algafood.api.dto.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestauranteDto {
 
	@JsonView({RestauranteView.Resumo.class,RestauranteView.ApenasNome.class})
	@ApiModelProperty(value="Id do restaurante",example="1")
	private Long id;
	@JsonView({RestauranteView.Resumo.class,RestauranteView.ApenasNome.class})
	@ApiModelProperty(value="Nome do restaurante",example="Restaurante Delicia Na Brasa")
	private String nome;
	@JsonView(RestauranteView.Resumo.class)
	@ApiModelProperty(value="Valor do frete",example="10.55")
	private BigDecimal taxaFrete;
	@JsonView(RestauranteView.Resumo.class)
	private CozinhaDto cozinha;
	private Boolean ativo;
	private Boolean aberto;
	private EnderecoDto endereco;
	
}
package com.algaworks.algafood.api.dto;

import java.math.BigDecimal;

import com.algaworks.algafood.api.dto.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestauranteDto {
 
	@JsonView({RestauranteView.Resumo.class,RestauranteView.ApenasNome.class})
	private Long id;
	@JsonView({RestauranteView.Resumo.class,RestauranteView.ApenasNome.class})
	private String nome;
	@JsonView(RestauranteView.Resumo.class)
	private BigDecimal taxaFrete;
	@JsonView(RestauranteView.Resumo.class)
	private CozinhaDto cozinha;
	private Boolean ativo;
	private Boolean aberto;
	private EnderecoDto endereco;
	
}
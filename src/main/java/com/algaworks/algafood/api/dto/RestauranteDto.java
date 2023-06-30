package com.algaworks.algafood.api.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestauranteDto {
 
	private Long id;
	private String nome;
	private BigDecimal taxaFrete;
	private CozinhaDto cozinha;
	private Boolean ativo;
	private Boolean aberto;
	private EnderecoDto endereco;
	
}
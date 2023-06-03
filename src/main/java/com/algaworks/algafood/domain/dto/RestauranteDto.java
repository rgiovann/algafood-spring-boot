package com.algaworks.algafood.domain.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RestauranteDto {

	@EqualsAndHashCode.Include
	private Long id;
	
	private String nome;
	
	private BigDecimal taxaFrete;

	private Long cozinhaId;
	
	@JsonIgnore
	private EnderecoDto endereco;

	
}
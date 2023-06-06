package com.algaworks.algafood.domain.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RestauranteDto {

	@EqualsAndHashCode.Include
	private Long id;
	
	@NotBlank
	private String nome;
	
	@PositiveOrZero
	private BigDecimal taxaFrete;

	@NotNull
	private Long cozinhaId;
	
	@JsonIgnore
	private EnderecoDto endereco;

	
}
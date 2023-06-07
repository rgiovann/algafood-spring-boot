package com.algaworks.algafood.domain.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.algaworks.algafood.core.validation.TaxaFrete;
import com.algaworks.algafood.core.validation.ValorZeroIncluiDescricao;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@ValorZeroIncluiDescricao(valorField = "taxaFrete",
						  descricaoField = "nome",
						  descricaoObrigatoria = "Frete Grátis")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RestauranteDto {

	@EqualsAndHashCode.Include
	private Long id;
	
	@NotBlank
	private String nome;
	
	//@PositiveOrZero
	@NotNull
	@TaxaFrete
	// @Multiplo(numero=5) exemplo didático
	private BigDecimal taxaFrete;

	@NotNull
	private Long cozinhaId;
	
	@JsonIgnore
	private EnderecoDto endereco;

	
}
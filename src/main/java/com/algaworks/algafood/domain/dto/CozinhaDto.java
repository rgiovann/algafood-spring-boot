package com.algaworks.algafood.domain.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CozinhaDto {

	@EqualsAndHashCode.Include
	private Long id;
	
	@NotBlank
	private String nome;

}
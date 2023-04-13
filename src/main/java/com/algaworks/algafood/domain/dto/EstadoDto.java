package com.algaworks.algafood.domain.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class EstadoDto {
	
	@EqualsAndHashCode.Include
	private Long id;
	private String nome;
	
	

}

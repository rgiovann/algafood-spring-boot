package com.algaworks.algafood.api.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.algaworks.algafood.core.validation.Groups;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class EstadoDto {
	
	@EqualsAndHashCode.Include
	@NotNull(groups = Groups.EstadoId.class)
	private Long id;
	
	@NotBlank
	private String nome;
	
	

}

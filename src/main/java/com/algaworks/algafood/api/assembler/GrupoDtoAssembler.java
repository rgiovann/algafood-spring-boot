package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.dto.GrupoDto;
import com.algaworks.algafood.domain.model.Grupo;

 @Component
public class GrupoDtoAssembler extends EntitytDtoAssembler<GrupoDto, Grupo>{

	public GrupoDtoAssembler(ModelMapper mapper) {
		super(mapper);
	}

}
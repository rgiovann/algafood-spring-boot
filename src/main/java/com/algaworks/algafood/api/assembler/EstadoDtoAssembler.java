package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.dto.EstadoDto;
import com.algaworks.algafood.domain.model.Estado;

 

@Component
public class EstadoDtoAssembler extends EntitytDtoAssembler<EstadoDto, Estado>{

	public EstadoDtoAssembler(ModelMapper mapper) {
		super(mapper);
	}

}
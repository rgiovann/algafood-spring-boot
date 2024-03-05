package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.EstadoController;
import com.algaworks.algafood.api.dto.EstadoDto;
import com.algaworks.algafood.domain.model.Estado;

 

@Component
public class EstadoDtoAssembler extends EntitytDtoAssembler<EstadoDto,Estado,EstadoController>{

	public EstadoDtoAssembler(ModelMapper mapper) {
		super(mapper, EstadoDto.class, EstadoController.class);
	}

	@Override
	public EstadoDto toModel(Estado entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
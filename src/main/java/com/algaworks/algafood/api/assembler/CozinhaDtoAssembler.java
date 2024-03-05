package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.CozinhaController;
import com.algaworks.algafood.api.dto.CozinhaDto;
import com.algaworks.algafood.domain.model.Cozinha;

 

@Component
public class CozinhaDtoAssembler extends EntitytDtoAssembler<CozinhaDto, Cozinha,CozinhaController>{

	public CozinhaDtoAssembler(ModelMapper mapper) {
		super(mapper,CozinhaDto.class,CozinhaController.class);
	}

	@Override
	public CozinhaDto toModel(Cozinha entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
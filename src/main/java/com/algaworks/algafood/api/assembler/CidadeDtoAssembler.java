package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.CidadeController;
import com.algaworks.algafood.api.dto.CidadeDto;
import com.algaworks.algafood.domain.model.Cidade;

 

@Component
public class CidadeDtoAssembler extends EntitytDtoAssembler<CidadeDto, Cidade,CidadeController>{

	public CidadeDtoAssembler(ModelMapper mapper) {
		super(mapper,CidadeDto.class,CidadeController.class);
	}

	@Override
	public CidadeDto toModel(Cidade entity) {
		// TODO Auto-generated method stub
		return null;
	}


}
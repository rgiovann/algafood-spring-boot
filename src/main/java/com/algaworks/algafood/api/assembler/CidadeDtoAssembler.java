package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.dto.CidadeDto;
import com.algaworks.algafood.domain.model.Cidade;

 

@Component
public class CidadeDtoAssembler extends EntitytDtoAssembler<CidadeDto, Cidade>{

	public CidadeDtoAssembler(ModelMapper mapper) {
		super(mapper);
	}

}
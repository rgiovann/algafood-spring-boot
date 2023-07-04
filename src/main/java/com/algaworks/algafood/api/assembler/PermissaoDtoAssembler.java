package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.dto.PermissaoDto;
import com.algaworks.algafood.domain.model.Permissao;

 @Component
public class PermissaoDtoAssembler extends EntitytDtoAssembler<PermissaoDto, Permissao>{

	public PermissaoDtoAssembler(ModelMapper mapper) {
		super(mapper);
	}

}
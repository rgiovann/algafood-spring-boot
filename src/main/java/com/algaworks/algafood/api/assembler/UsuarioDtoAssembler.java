package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.dto.UsuarioDto;
import com.algaworks.algafood.domain.model.Usuario;

 

@Component
public class UsuarioDtoAssembler extends EntitytDtoAssembler<UsuarioDto, Usuario>{

	public UsuarioDtoAssembler(ModelMapper mapper) {
		super(mapper);
	}

}
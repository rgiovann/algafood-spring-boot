package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.UsuarioController;
import com.algaworks.algafood.api.dto.UsuarioDto;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.infraestructure.configuration.Mapper;

 

@Component
public class UsuarioDtoAssembler extends EntitytDtoAssembler<UsuarioDto, Usuario,UsuarioController>{

	public UsuarioDtoAssembler(Mapper mapper) {
		super(mapper,UsuarioDto.class,UsuarioController.class);
	}

	@Override
	public UsuarioDto toModel(Usuario entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
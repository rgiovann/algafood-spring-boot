package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.GrupoController;
import com.algaworks.algafood.api.dto.GrupoDto;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.infraestructure.configuration.Mapper;

 @Component
public class GrupoDtoAssembler extends EntitytDtoAssembler<GrupoDto, Grupo, GrupoController>{

	public GrupoDtoAssembler(Mapper mapper) {
		super(mapper,GrupoDto.class, GrupoController.class);
	}

	@Override
	public GrupoDto toModel(Grupo entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
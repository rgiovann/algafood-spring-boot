package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.GrupoPermissaoController;
import com.algaworks.algafood.api.dto.PermissaoDto;
import com.algaworks.algafood.domain.model.Permissao;

 @Component
public class PermissaoDtoAssembler extends EntitytDtoAssembler<PermissaoDto, Permissao,GrupoPermissaoController>{

	public PermissaoDtoAssembler(ModelMapper mapper) {
		super(mapper,PermissaoDto.class,GrupoPermissaoController.class);
	}

	@Override
	public PermissaoDto toModel(Permissao entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
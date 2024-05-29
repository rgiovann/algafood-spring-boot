package com.algaworks.algafood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Arrays;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.CozinhaController;
import com.algaworks.algafood.api.controller.GrupoPermissaoController;
import com.algaworks.algafood.api.dto.PermissaoDto;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.infraestructure.configuration.Mapper;

 @Component
public class PermissaoDtoAssembler extends EntitytDtoAssembler<PermissaoDto, Permissao,GrupoPermissaoController>{

	public PermissaoDtoAssembler(Mapper mapper) {
		super(mapper,GrupoPermissaoController.class,PermissaoDto.class);
	}


	@Override
	public List<Link> constructLinks(Permissao entityObject) {
		// TODO Auto-generated method stub
		return   
				Arrays.asList(
						linkTo( methodOn(CozinhaController.class).buscar(entityObject.getId())).withSelfRel(),	
						linkTo( CozinhaController.class).withRel(("permissoes"))
						);
	}

}
package com.algaworks.algafood.api.assembler;

import java.util.Arrays;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.GrupoPermissaoController;
import com.algaworks.algafood.api.dto.PermissaoDto;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.infraestructure.configuration.Mapper;

import lombok.Getter;
import lombok.Setter;

 @Component
 @Getter
 @Setter
public class PermissaoDtoAssembler extends EntitytDtoAssembler<PermissaoDto, Permissao,GrupoPermissaoController>{

	private Long groupId;
	private AlgaLinks permissaoLinks;
	
	public PermissaoDtoAssembler(Mapper mapper, AlgaLinks permissaoLinks) {
		super(mapper,GrupoPermissaoController.class,PermissaoDto.class);
		this.permissaoLinks = permissaoLinks;
	}


	@Override
	public List<Link> constructLinks(Permissao entityObject) {
		return Arrays.asList();  // retorna empty pois não tem buscar permissão, só listar.
	}

	@Override
	public Link constructCollectionLink() {
 		return permissaoLinks.linkToPermissoes(this.groupId);
 	}

}
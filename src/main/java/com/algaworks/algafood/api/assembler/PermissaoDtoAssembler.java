package com.algaworks.algafood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Arrays;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

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
	public PermissaoDtoAssembler(Mapper mapper) {
		super(mapper,GrupoPermissaoController.class,PermissaoDto.class);
	}


	@Override
	public List<Link> constructLinks(Permissao entityObject) {
		// TODO Auto-generated method stub
		return   
				Arrays.asList();  // retorna empty pois não tem buscar permissão, só listar.
	}


//	public CollectionModel<PermissaoDto> setPermissionsGroupLink (CollectionModel<PermissaoDto> collectionPermissaoDto,Long groupId){
//		collectionPermissaoDto.removeLinks();
//		collectionPermissaoDto.add(linkTo(methodOn(GrupoPermissaoController.class)
//				.listarPermissoes(groupId)).withSelfRel());
//		return null;
//	}


	@Override
	public Link constructCollectionLink() {
 		return linkTo(methodOn(GrupoPermissaoController.class).listarPermissoes(groupId)).withSelfRel();
 	}

}
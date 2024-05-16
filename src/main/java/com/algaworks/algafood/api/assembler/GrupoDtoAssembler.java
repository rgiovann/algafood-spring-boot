package com.algaworks.algafood.api.assembler;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Arrays;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

 import com.algaworks.algafood.api.controller.GrupoController;
 import com.algaworks.algafood.domain.model.Grupo;
 import com.algaworks.algafood.api.dto.GrupoDto; 
import com.algaworks.algafood.infraestructure.configuration.Mapper;

 @Component
public class GrupoDtoAssembler extends EntitytDtoAssembler<GrupoDto, Grupo, GrupoController >{

	public GrupoDtoAssembler(Mapper mapper) {
 		super(mapper,GrupoController.class,GrupoDto.class);
	}
	
	@Override
	public List<Link> constructLinks(Grupo entityObject) {
		// TODO Auto-generated method stub
		return Arrays.asList(linkTo( methodOn(GrupoController.class).buscar(entityObject.getId()))
                .withSelfRel(),
        linkTo( methodOn(GrupoController.class).listar())
	               .withRel(("grupos")) 	    		               
	);
	}	
 }
 
 /*
 @Component
 public class CidadeDtoAssembler extends EntitytDtoAssembler<CidadeDto,Cidade>{

 	public CidadeDtoAssembler(Mapper mapper,CidadeDto dtoObject) {
 		super(mapper,CidadeDto.class,
 				Arrays.asList(linkTo( methodOn(CidadeController.class).buscar(dtoObject.getId()))
 		                              .withSelfRel(),
 		        	          linkTo( methodOn(CidadeController.class).listar())
 			    		               .withRel(("cidades")),
 			    		      linkTo( methodOn(EstadoController.class).buscar(dtoObject.getEstado().getId()))
 			                              .withSelfRel()			    		               
 						),
 				linkTo(CidadeController.class).withSelfRel());
 	}

 }
 */
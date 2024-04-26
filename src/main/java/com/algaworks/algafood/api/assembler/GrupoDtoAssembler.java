package com.algaworks.algafood.api.assembler;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.GrupoController;
import com.algaworks.algafood.api.dto.GrupoDto;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.infraestructure.configuration.Mapper;

 @Component
public class GrupoDtoAssembler extends EntitytDtoAssembler<GrupoDto, Grupo >{

	public GrupoDtoAssembler(Mapper mapper, GrupoDto grupoDto) {
 		super(mapper,GrupoDto.class,
 				Arrays.asList(linkTo( methodOn(GrupoController.class).buscar(grupoDto.getId()))
 		                              .withSelfRel(),
 		        	          linkTo( methodOn(GrupoController.class).listar())
 			    		               .withRel(("cidades")) 		    		               
 						),
 				linkTo(GrupoController.class).withSelfRel());	}

 
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
package com.algaworks.algafood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Arrays;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.EstadoController;
import com.algaworks.algafood.api.dto.EstadoDto;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.infraestructure.configuration.Mapper;

 

@Component
public class EstadoDtoAssembler extends EntitytDtoAssembler<EstadoDto,Estado,EstadoController>{

	public EstadoDtoAssembler(Mapper mapper ) {
		super(mapper, EstadoController.class,EstadoDto.class);

	}

	@Override
	public List<Link> constructLinks(Estado entityObject) {
		 
 	return Arrays.asList(linkTo( methodOn(EstadoController.class).buscar(entityObject.getId()))
 				 .withSelfRel(),
 				 linkTo( methodOn(EstadoController.class).listar())
 				 .withRel(("estados")) 		    		               
);
	}

	@Override
	public Link constructCollectionLink() {
 		return linkTo(EstadoController.class).withSelfRel(); 
	}

 

}

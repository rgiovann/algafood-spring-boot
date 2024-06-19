package com.algaworks.algafood.api.assembler;

import java.util.Arrays;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.EstadoController;
import com.algaworks.algafood.api.dto.EstadoDto;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.infraestructure.configuration.Mapper;

 

@Component
public class EstadoDtoAssembler extends EntitytDtoAssembler<EstadoDto,Estado,EstadoController>{

	private AlgaLinks estadoLinks;

	public EstadoDtoAssembler(Mapper mapper, AlgaLinks estadoLinks ) {
		super(mapper, EstadoController.class,EstadoDto.class);
		this.estadoLinks = estadoLinks;

	}

	@Override
	public List<Link> constructLinks(Estado entityObject) {
		 
 	return Arrays.asList(estadoLinks.linkToEstado(entityObject.getId()),
 						 estadoLinks.linkToEstado("estados") 		    		               
);
	}

	@Override
	public Link constructCollectionLink() {
 		return estadoLinks.linkToEstado(); 		    		               
 
	}

 

}

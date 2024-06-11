package com.algaworks.algafood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Arrays;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.CidadeController;
import com.algaworks.algafood.api.controller.EstadoController;
import com.algaworks.algafood.api.dto.CidadeDto;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.infraestructure.configuration.Mapper;

 

@Component
public class CidadeDtoAssembler extends EntitytDtoAssembler<CidadeDto,Cidade,CidadeController>{

	public CidadeDtoAssembler(Mapper mapper) {
		super(mapper,CidadeController.class,CidadeDto.class);

	}

	@Override
	public List<Link> constructLinks(Cidade entityObject) {
		return Arrays.asList(linkTo( methodOn(CidadeController.class).buscar(entityObject.getId()))
                .withSelfRel(),
        linkTo( methodOn(CidadeController.class).listar())
	               .withRel(("cidades")),
	      linkTo( methodOn(EstadoController.class).buscar(entityObject.getEstado().getId()))
                    .withSelfRel(),
         linkTo( methodOn(EstadoController.class).listar())
 	               .withRel(("estados"))
	);
	}

	@Override
	public  Link  constructCollectionLink( ) {
 		return linkTo(CidadeController.class).withSelfRel();
	}
	

}
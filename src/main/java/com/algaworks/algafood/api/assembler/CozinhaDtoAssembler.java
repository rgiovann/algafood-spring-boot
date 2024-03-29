package com.algaworks.algafood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.CozinhaController;
import com.algaworks.algafood.api.dto.CidadeDto;
import com.algaworks.algafood.api.dto.CozinhaDto;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.infraestructure.configuration.Mapper;

 

@Component
public class CozinhaDtoAssembler extends EntitytDtoAssembler<CozinhaDto,Cozinha>{

	public CozinhaDtoAssembler(Mapper mapper,CidadeDto dtoObject) {
		super(mapper,CozinhaDto.class,
				Arrays.asList(linkTo( methodOn(CozinhaController.class).buscar(dtoObject.getId()))
		                              .withSelfRel(),
		        	          linkTo( methodOn(CozinhaController.class).listarPaged(null))
			    		               .withRel(("cidades"))		    		               
						),
				linkTo(CozinhaController.class).withSelfRel());
	}

}
package com.algaworks.algafood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Arrays;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.CozinhaController;
import com.algaworks.algafood.api.dto.CozinhaDto;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.infraestructure.configuration.Mapper;

 

@Component
public class CozinhaDtoAssembler extends EntitytDtoAssembler<CozinhaDto,Cozinha,CozinhaController>{

	public CozinhaDtoAssembler(Mapper mapper) {
		super(mapper,CozinhaController.class,CozinhaDto.class);
	}

	@Override
	public  List<Link> constructLinks(Cozinha cozinha ) {
		return  
				Arrays.asList(
						linkTo( methodOn(CozinhaController.class).buscar(cozinha.getId())).withSelfRel(),	
						linkTo( CozinhaController.class).withRel(("cozinhas"))
						);
		//,linkTo(CozinhaController.class).withSelfRel() ;
	}


}
package com.algaworks.algafood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Arrays;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.CozinhaController;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.dto.RestauranteDto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.infraestructure.configuration.Mapper;

 

@Component
public class RestauranteDtoAssembler extends EntitytDtoAssembler<RestauranteDto, Restaurante,RestauranteController>{

	public RestauranteDtoAssembler(Mapper mapper) {
		super(mapper,RestauranteController.class,RestauranteDto.class);
	}


	@Override
	public List<Link> constructLinks(Restaurante entityObject) {
		return  
				Arrays.asList(
						linkTo( methodOn(CozinhaController.class).buscar(entityObject.getId())).withSelfRel(),	
						linkTo( CozinhaController.class).withRel(("restaurantes"))
						);
	}

}
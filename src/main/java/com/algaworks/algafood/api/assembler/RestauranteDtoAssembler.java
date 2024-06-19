package com.algaworks.algafood.api.assembler;

import java.util.Arrays;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.dto.RestauranteDto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.infraestructure.configuration.Mapper;

 

@Component
public class RestauranteDtoAssembler extends EntitytDtoAssembler<RestauranteDto, Restaurante,RestauranteController>{

    private AlgaLinks restauranteLinks;

	public RestauranteDtoAssembler(Mapper mapper,AlgaLinks restauranteLinks) {
		super(mapper,RestauranteController.class,RestauranteDto.class);
		this.restauranteLinks = restauranteLinks;
	}


	@Override
	public List<Link> constructLinks(Restaurante entityObject) {
		return  
				Arrays.asList( restauranteLinks.linkToRestaurante(entityObject.getId()),
						       restauranteLinks.linkToRestaurante("restaurantes"));
	}


	@Override
	public Link constructCollectionLink() {
 		return restauranteLinks.linkToRestaurante();

	}

}
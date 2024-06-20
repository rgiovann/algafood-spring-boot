package com.algaworks.algafood.api.assembler;

import java.util.Arrays;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.dto.RestauranteBasicoDto;
import com.algaworks.algafood.api.dto.RestauranteDto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.infraestructure.configuration.Mapper;

 

@Component
public class RestauranteBasicoDtoAssembler extends EntitytDtoAssembler<RestauranteBasicoDto, Restaurante,RestauranteController>{

    private AlgaLinks restauranteLinks;

	public RestauranteBasicoDtoAssembler(Mapper mapper,AlgaLinks restauranteLinks) {
		super(mapper,RestauranteController.class,RestauranteBasicoDto.class);
		this.restauranteLinks = restauranteLinks;
	}


	@Override
	public List<Link> constructLinks(Restaurante entityObject) {
		return  
				Arrays.asList( restauranteLinks.linkToRestaurante(entityObject.getId()),
						       restauranteLinks.linkToRestaurante("restaurantes"),
						       restauranteLinks.linkToCozinha(entityObject.getCozinha().getId()),
						       restauranteLinks.linkToCozinha("cozinhas"));
	}


	@Override
	public Link constructCollectionLink() {
 		return restauranteLinks.linkToRestaurante();

	}

}
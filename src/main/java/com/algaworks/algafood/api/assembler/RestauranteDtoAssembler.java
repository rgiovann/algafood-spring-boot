package com.algaworks.algafood.api.assembler;

import java.util.ArrayList;
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
		
	    // Create a modifiable list
	    List<Link> listaDeLinks = new ArrayList<>();
		
	    listaDeLinks.add(restauranteLinks.linkToRestaurante(entityObject.getId()));
	    listaDeLinks.add(restauranteLinks.linkToRestaurante("restaurantes"));		 
	    listaDeLinks.add(restauranteLinks.linkToRestaurante("restaurantes"));
	    listaDeLinks.add(restauranteLinks.linkToCozinha(entityObject.getCozinha().getId()));
	    listaDeLinks.add(restauranteLinks.linkToCozinha("cozinhas"));
	    listaDeLinks.add(restauranteLinks.linkToCidade(entityObject.getEndereco().getCidade().getId()));
	    listaDeLinks.add(restauranteLinks.linkToCidade("cidades"));
	    listaDeLinks.add(restauranteLinks.linkToFormaPagamentoRestaurante(entityObject.getId()));
	    listaDeLinks.add(restauranteLinks.linkToUsuarioResponsavel(entityObject.getId()));

		if(entityObject.getAtivo()) {
		    listaDeLinks.add(restauranteLinks.linkToInativarRestaurante(entityObject.getId(), "inativar"));
		}
		else
		{
		    listaDeLinks.add(restauranteLinks.linkToAtivarRestaurante(entityObject.getId(), "ativar"));
		}
		
		if(entityObject.getAberto()) {
		    listaDeLinks.add(restauranteLinks.linkToAbrirRestaurante(entityObject.getId(), "fechar"));
		}
		else
		{
		    listaDeLinks.add(restauranteLinks.linkToFecharRestaurante(entityObject.getId(), "abrir"));
		}
						 
		return listaDeLinks;
	}


	@Override
	public Link constructCollectionLink() {
 		return restauranteLinks.linkToRestaurante();

	}

}
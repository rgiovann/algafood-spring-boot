package com.algaworks.algafood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Arrays;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.CozinhaController;
import com.algaworks.algafood.api.controller.RestauranteProdutoController;
import com.algaworks.algafood.api.dto.ProdutoDto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.infraestructure.configuration.Mapper;

 

@Component
public class ProdutoDtoAssembler extends EntitytDtoAssembler<ProdutoDto, Produto,RestauranteProdutoController>{

	public ProdutoDtoAssembler(Mapper mapper) {
		super(mapper,RestauranteProdutoController.class,ProdutoDto.class);
	}


	@Override
	public List<Link> constructLinks(Produto entityObject) {
		// TODO Auto-generated method stub
		return  
				Arrays.asList(
						linkTo( methodOn(CozinhaController.class).buscar(entityObject.getId())).withSelfRel(),	
						linkTo( CozinhaController.class).withRel(("produtos"))
						);
	}

}
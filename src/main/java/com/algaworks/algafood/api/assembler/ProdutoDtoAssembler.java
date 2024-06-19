package com.algaworks.algafood.api.assembler;

import java.util.Arrays;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.RestauranteProdutoController;
import com.algaworks.algafood.api.dto.ProdutoDto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.infraestructure.configuration.Mapper;

import lombok.Getter;
import lombok.Setter;

 

@Component
@Setter
@Getter
public class ProdutoDtoAssembler extends EntitytDtoAssembler<ProdutoDto, Produto,RestauranteProdutoController>{
    
	private Long restauranteId;
	private AlgaLinks produtoLinks;
	
	public ProdutoDtoAssembler(Mapper mapper,AlgaLinks produtoLinks) {
		super(mapper,RestauranteProdutoController.class,ProdutoDto.class);
		this.produtoLinks = produtoLinks;
	}


	@Override
	public List<Link> constructLinks(Produto entityObject) {
		return  
				Arrays.asList(produtoLinks.linkToProduto(entityObject.getRestaurante().getId(), entityObject.getId()),
						      produtoLinks.linkToProduto(entityObject.getRestaurante().getId())					 
						);
	}


	@Override
	public Link constructCollectionLink() {
 		return produtoLinks.linkToProduto(this.restauranteId);

	}
}
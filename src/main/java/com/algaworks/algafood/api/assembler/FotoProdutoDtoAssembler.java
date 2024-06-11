package com.algaworks.algafood.api.assembler;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.RestauranteFotoController;
import com.algaworks.algafood.api.dto.FotoProdutoDto;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.infraestructure.configuration.Mapper;

 

@Component
public class FotoProdutoDtoAssembler extends EntitytDtoAssembler<FotoProdutoDto, FotoProduto,
                                                                 RestauranteFotoController>{

	public FotoProdutoDtoAssembler(Mapper mapper) {
		super(mapper,RestauranteFotoController.class,FotoProdutoDto.class);
	}

	@Override
	public List<Link> constructLinks(FotoProduto entityObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Link constructCollectionLink() {
		// TODO Auto-generated method stub
		return null;
	}



	 }
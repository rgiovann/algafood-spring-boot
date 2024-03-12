package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.RestauranteProdutoController;
import com.algaworks.algafood.api.dto.ProdutoDto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.infraestructure.configuration.Mapper;

 

@Component
public class ProdutoDtoAssembler extends EntitytDtoAssembler<ProdutoDto, Produto,RestauranteProdutoController>{

	public ProdutoDtoAssembler(Mapper mapper) {
		super(mapper,ProdutoDto.class,RestauranteProdutoController.class);
	}

	@Override
	public ProdutoDto toModel(Produto entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
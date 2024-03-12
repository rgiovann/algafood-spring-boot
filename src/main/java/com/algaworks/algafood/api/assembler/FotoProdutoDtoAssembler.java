package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.RestauranteFotoController;
import com.algaworks.algafood.api.dto.FotoProdutoDto;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.infraestructure.configuration.Mapper;

 

@Component
public class FotoProdutoDtoAssembler extends EntitytDtoAssembler<FotoProdutoDto, FotoProduto,
                                                                 RestauranteFotoController>{

	public FotoProdutoDtoAssembler(Mapper mapper) {
		super(mapper,FotoProdutoDto.class,RestauranteFotoController.class);
	}



	@Override
	public FotoProdutoDto toModel(FotoProduto entity) {
		// TODO Auto-generated method stub
		return null;
	}}
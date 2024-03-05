package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.dto.RestauranteDto;
import com.algaworks.algafood.domain.model.Restaurante;

 

@Component
public class RestauranteDtoAssembler extends EntitytDtoAssembler<RestauranteDto, Restaurante,RestauranteController>{

	public RestauranteDtoAssembler(ModelMapper mapper) {
		super(mapper,RestauranteDto.class,RestauranteController.class);
	}

	@Override
	public RestauranteDto toModel(Restaurante entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
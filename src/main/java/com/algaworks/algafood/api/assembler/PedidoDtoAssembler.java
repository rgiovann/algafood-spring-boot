package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.dto.PedidoDto;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.infraestructure.configuration.Mapper;

 @Component
public class PedidoDtoAssembler extends EntitytDtoAssembler<PedidoDto, Pedido, PedidoController>{

	public PedidoDtoAssembler(Mapper mapper) {
		super(mapper,PedidoDto.class,PedidoController.class);
	}

	@Override
	public PedidoDto toModel(Pedido entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
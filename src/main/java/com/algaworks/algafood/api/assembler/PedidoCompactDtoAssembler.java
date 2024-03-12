package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.dto.PedidoCompactDto;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.infraestructure.configuration.Mapper;

 @Component
public class PedidoCompactDtoAssembler extends EntitytDtoAssembler<PedidoCompactDto,Pedido,
                                                                   PedidoController>{

	public PedidoCompactDtoAssembler(Mapper mapper) {
		super(mapper,PedidoCompactDto.class,PedidoController.class);
	}

	@Override
	public PedidoCompactDto toModel(Pedido entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
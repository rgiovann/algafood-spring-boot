package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.dto.PedidoDto;
import com.algaworks.algafood.domain.model.Pedido;

 @Component
public class PedidoDtoAssembler extends EntitytDtoAssembler<PedidoDto, Pedido>{

	public PedidoDtoAssembler(ModelMapper mapper) {
		super(mapper);
	}

}
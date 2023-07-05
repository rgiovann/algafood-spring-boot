package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.dto.PedidoCompactDto;
import com.algaworks.algafood.domain.model.Pedido;

 @Component
public class PedidoCompactDtoAssembler extends EntitytDtoAssembler<PedidoCompactDto, Pedido>{

	public PedidoCompactDtoAssembler(ModelMapper mapper) {
		super(mapper);
	}

}
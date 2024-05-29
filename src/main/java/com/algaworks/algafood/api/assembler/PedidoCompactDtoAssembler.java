package com.algaworks.algafood.api.assembler;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.dto.PedidoCompactDto;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.infraestructure.configuration.Mapper;

 @Component
public class PedidoCompactDtoAssembler extends EntitytDtoAssembler<PedidoCompactDto,Pedido,
                                                                   PedidoController>{

	public PedidoCompactDtoAssembler(Mapper mapper) {
		super(mapper,PedidoController.class, PedidoCompactDto.class);
	}

	@Override
	public List<Link> constructLinks(Pedido entityObject) {
		return null;
	}

 

}
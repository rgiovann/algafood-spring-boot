package com.algaworks.algafood.api.assembler;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.input.PedidoInput;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.infraestructure.configuration.Mapper;

@Component
public class PedidoInputDisassembler extends EntityInputDisassembler<PedidoInput, Pedido>{

	public PedidoInputDisassembler(Mapper mapper) {
		super(mapper);
	}
}

package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.input.PedidoInput;
import com.algaworks.algafood.domain.model.Pedido;

@Component
public class PedidoInputDisassembler extends EntityInputDisassembler<PedidoInput, Pedido>{

	public PedidoInputDisassembler(ModelMapper mapper) {
		super(mapper);
	}
}

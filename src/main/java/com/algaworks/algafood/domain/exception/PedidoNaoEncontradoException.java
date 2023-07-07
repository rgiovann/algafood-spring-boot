package com.algaworks.algafood.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public PedidoNaoEncontradoException(String codigoPedidoId) {
		super(String.format("Pedido de código %s não encontrada.",codigoPedidoId) );
	}
	
 

}

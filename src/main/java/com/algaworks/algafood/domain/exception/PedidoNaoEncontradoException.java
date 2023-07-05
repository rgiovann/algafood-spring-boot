package com.algaworks.algafood.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public PedidoNaoEncontradoException(String msg) {
		super(msg);
	}
	
	public PedidoNaoEncontradoException(Long pedidoId) {
		// chamando o construtor anterior;
		 this(String.format("Pedido de código %d não encontrada.",pedidoId));
	}

}

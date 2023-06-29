package com.algaworks.algafood.domain.exception;

//@ResponseStatus(value = HttpStatus.NOT_FOUND)
// não precisa responsestatus pois a classe pai já tem esse annotation
public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;
	
	public ProdutoNaoEncontradoException(String msg) {
		super(msg);
	}
	
	public ProdutoNaoEncontradoException(Long produtoId) {
		// chamando o construtor anterior;
		 this(String.format("Produto de código %d não encontrado.",produtoId));
	}


}


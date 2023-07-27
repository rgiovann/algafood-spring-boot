package com.algaworks.algafood.domain.exception;

//@ResponseStatus(value = HttpStatus.NOT_FOUND)
// não precisa responsestatus pois a classe pai já tem esse annotation
public class FotoNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;
	
	public FotoNaoEncontradaException(String msg) {
		super(msg);
	}
	
	public FotoNaoEncontradaException(Long produtoId,Long restauranteId) {
		// chamando o construtor anterior;
		this(String.format("Não existe uma foto de produto com código %d para o restaurante de código %d", 
                produtoId, restauranteId));
	}

}


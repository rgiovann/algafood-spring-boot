package com.algaworks.algafood.domain.exception;

//@ResponseStatus(value = HttpStatus.NOT_FOUND)
// não precisa responsestatus pois a classe pai já tem esse annotation
public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;
	
	public GrupoNaoEncontradoException(String msg) {
		super(msg);
	}
	
	public GrupoNaoEncontradoException(Long grupoId) {
		// chamando o construtor anterior;
		 this(String.format("Grupo de código %d não encontrado.",grupoId));
	}


}


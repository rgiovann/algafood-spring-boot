package com.algaworks.algafood.domain.exception;

//@ResponseStatus(value = HttpStatus.NOT_FOUND)
// não precisa responsestatus pois a classe pai já tem esse annotation
public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;
	
	public UsuarioNaoEncontradoException(String msg) {
		super(msg);
	}
	
	public UsuarioNaoEncontradoException(Long usuarioId) {
		// chamando o construtor anterior;
		 this(String.format("Usuário de código %d não encontrado.",usuarioId));
	}


}


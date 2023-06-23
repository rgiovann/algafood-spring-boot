package com.algaworks.algafood.domain.exception;

//@ResponseStatus(value = HttpStatus.CONFLICT)
// tratado pela classe ApiExceptionHandler
public class SenhaAtualNaoConfereException extends NegocioException {

	private static final long serialVersionUID = 1L;
	
	public SenhaAtualNaoConfereException(String msg) {
		super(msg);
	}

}


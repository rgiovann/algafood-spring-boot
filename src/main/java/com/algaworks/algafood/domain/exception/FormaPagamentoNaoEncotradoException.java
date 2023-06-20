package com.algaworks.algafood.domain.exception;

//@ResponseStatus(value = HttpStatus.NOT_FOUND)
// não precisa responsestatus pois a classe pai já tem esse annotation
public class FormaPagamentoNaoEncotradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;
	
	public FormaPagamentoNaoEncotradoException(String msg) {
		super(msg);
	}
	
	public FormaPagamentoNaoEncotradoException(Long formaDePagamentoId) {
		// chamando o construtor anterior;
		 this(String.format("Forma de pagamento de código %d não encontrado.",formaDePagamentoId));
	}


}


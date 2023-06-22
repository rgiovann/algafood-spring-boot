package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.input.FormaPagamentoNomeInput;
import com.algaworks.algafood.domain.model.FormaPagamento;

@Component
public class FormaPagamentoInputDisassembler extends EntityInputDisassembler<FormaPagamentoNomeInput, FormaPagamento>{

	public FormaPagamentoInputDisassembler(ModelMapper mapper) {
		super(mapper);
	}
}

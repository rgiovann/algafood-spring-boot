package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.input.FormaPagamentoInput;
import com.algaworks.algafood.domain.model.FormaPagamento;

@Component
public class FormaPagamentoInputDisassembler extends EntityInputDisassembler<FormaPagamentoInput, FormaPagamento>{

	public FormaPagamentoInputDisassembler(ModelMapper mapper) {
		super(mapper);
		// TODO Auto-generated constructor stub
	}
}

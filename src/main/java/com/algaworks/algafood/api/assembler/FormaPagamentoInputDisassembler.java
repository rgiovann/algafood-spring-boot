package com.algaworks.algafood.api.assembler;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.input.FormaPagamentoNomeInput;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.infraestructure.configuration.Mapper;

@Component
public class FormaPagamentoInputDisassembler extends EntityInputDisassembler<FormaPagamentoNomeInput, FormaPagamento>{

	public FormaPagamentoInputDisassembler(Mapper mapper) {
		super(mapper);
	}
}

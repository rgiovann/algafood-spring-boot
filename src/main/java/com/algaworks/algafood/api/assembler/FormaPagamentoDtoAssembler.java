package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.dto.FormaPagamentoDto;
import com.algaworks.algafood.domain.model.FormaPagamento;

 

@Component
public class FormaPagamentoDtoAssembler extends EntitytDtoAssembler<FormaPagamentoDto, FormaPagamento>{

	public FormaPagamentoDtoAssembler(ModelMapper mapper) {
		super(mapper);
	}

}
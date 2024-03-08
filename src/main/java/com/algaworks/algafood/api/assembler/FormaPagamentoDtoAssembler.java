package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.FormaPagamentoController;
import com.algaworks.algafood.api.dto.FormaPagamentoDto;
import com.algaworks.algafood.domain.model.FormaPagamento;

 

@Component
public class FormaPagamentoDtoAssembler extends EntitytDtoAssembler<FormaPagamentoDto, 
                                                                    FormaPagamento,
                                                                    FormaPagamentoController>{

	public FormaPagamentoDtoAssembler(ModelMapper mapper) {
		super(mapper,FormaPagamentoDto.class,FormaPagamentoController.class);
	}

	@Override
	public FormaPagamentoDto toModel(FormaPagamento entity) {
		return null;
	}

}
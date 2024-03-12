package com.algaworks.algafood.api.assembler;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.input.CidadeInput;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.infraestructure.configuration.Mapper;

@Component
public class CidadeInputDisassembler extends EntityInputDisassembler<CidadeInput, Cidade>{

	public CidadeInputDisassembler(Mapper mapper) {
		super(mapper);
	}	
}

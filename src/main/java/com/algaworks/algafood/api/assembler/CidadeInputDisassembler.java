package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.input.CidadeInput;
import com.algaworks.algafood.domain.model.Cidade;

@Component
public class CidadeInputDisassembler extends EntityInputDisassembler<CidadeInput, Cidade>{

	public CidadeInputDisassembler(ModelMapper mapper) {
		super(mapper);
	}	
}

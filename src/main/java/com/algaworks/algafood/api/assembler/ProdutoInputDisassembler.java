package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.input.ProdutoInput;
import com.algaworks.algafood.domain.model.Produto;

@Component
public class ProdutoInputDisassembler extends EntityInputDisassembler<ProdutoInput, Produto>{

	public ProdutoInputDisassembler(ModelMapper mapper) {
		super(mapper);
	}
}

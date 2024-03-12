package com.algaworks.algafood.api.assembler;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.input.ProdutoInput;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.infraestructure.configuration.Mapper;

@Component
public class ProdutoInputDisassembler extends EntityInputDisassembler<ProdutoInput, Produto>{

	public ProdutoInputDisassembler(Mapper mapper) {
		super(mapper);
	}
}

package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.dto.ProdutoDto;
import com.algaworks.algafood.domain.model.Produto;

 

@Component
public class ProdutoDtoAssembler extends EntitytDtoAssembler<ProdutoDto, Produto>{

	public ProdutoDtoAssembler(ModelMapper mapper) {
		super(mapper);
	}

}
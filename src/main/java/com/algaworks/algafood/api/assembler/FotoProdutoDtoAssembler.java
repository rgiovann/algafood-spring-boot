package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.dto.FotoProdutoDto;
import com.algaworks.algafood.domain.model.FotoProduto;

 

@Component
public class FotoProdutoDtoAssembler extends EntitytDtoAssembler<FotoProdutoDto, FotoProduto>{

	public FotoProdutoDtoAssembler(ModelMapper mapper) {
		super(mapper);
	}

}
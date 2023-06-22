package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.input.EstadoNomeInput;
import com.algaworks.algafood.domain.model.Estado;

@Component
public class EstadoNomeInputDisassembler extends EntityInputDisassembler<EstadoNomeInput, Estado>{

	public EstadoNomeInputDisassembler(ModelMapper mapper) {
		super(mapper);
	}
}

package com.algaworks.algafood.api.assembler;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.input.EstadoNomeInput;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.infraestructure.configuration.Mapper;

@Component
public class EstadoNomeInputDisassembler extends EntityInputDisassembler<EstadoNomeInput, Estado>{

	public EstadoNomeInputDisassembler(Mapper mapper) {
		super(mapper);
	}
}

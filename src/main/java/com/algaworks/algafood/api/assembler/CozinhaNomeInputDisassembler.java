package com.algaworks.algafood.api.assembler;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.input.CozinhaNomeInput;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.infraestructure.configuration.Mapper;

@Component
public class CozinhaNomeInputDisassembler extends EntityInputDisassembler<CozinhaNomeInput, Cozinha>{

	public CozinhaNomeInputDisassembler(Mapper mapper) {
		super(mapper);
	}
}

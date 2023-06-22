package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.input.CozinhaNomeInput;
import com.algaworks.algafood.domain.model.Cozinha;

@Component
public class CozinhaNomeInputDisassembler extends EntityInputDisassembler<CozinhaNomeInput, Cozinha>{

	public CozinhaNomeInputDisassembler(ModelMapper mapper) {
		super(mapper);
	}
}

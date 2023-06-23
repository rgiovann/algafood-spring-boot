package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.input.GrupoNomeInput;
import com.algaworks.algafood.domain.model.Grupo;

@Component
public class GrupoNomeInputDisassembler extends EntityInputDisassembler<GrupoNomeInput, Grupo>{

	public GrupoNomeInputDisassembler(ModelMapper mapper) {
		super(mapper);
	}

}

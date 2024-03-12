package com.algaworks.algafood.api.assembler;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.input.GrupoNomeInput;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.infraestructure.configuration.Mapper;

@Component
public class GrupoNomeInputDisassembler extends EntityInputDisassembler<GrupoNomeInput, Grupo>{

	public GrupoNomeInputDisassembler(Mapper mapper) {
		super(mapper);
	}

}

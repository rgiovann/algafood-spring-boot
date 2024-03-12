package com.algaworks.algafood.api.assembler;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.input.UsuarioInput;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.infraestructure.configuration.Mapper;

@Component
public class UsuarioInputDisassembler extends EntityInputDisassembler<UsuarioInput, Usuario> {

	public UsuarioInputDisassembler(Mapper mapper) {
		super(mapper);
	}
}

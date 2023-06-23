package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.input.UsuarioInput;
import com.algaworks.algafood.domain.model.Usuario;

@Component
public class UsuarioInputDisassembler extends EntityInputDisassembler<UsuarioInput, Usuario> {

	public UsuarioInputDisassembler(ModelMapper mapper) {
		super(mapper);
	}
}

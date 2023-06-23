package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.input.UsuarioSemSenhaInput;
import com.algaworks.algafood.domain.model.Usuario;

@Component
public class UsuarioSemSenhaInputDisassembler extends EntityInputDisassembler<UsuarioSemSenhaInput, Usuario> {

	public UsuarioSemSenhaInputDisassembler(ModelMapper mapper) {
		super(mapper);
	}
}

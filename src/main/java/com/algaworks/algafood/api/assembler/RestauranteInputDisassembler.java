package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.input.RestauranteInput;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteInputDisassembler extends EntityInputDisassembler<RestauranteInput, Restaurante> {

	public RestauranteInputDisassembler(ModelMapper mapper) {
		super(mapper);
	}
}

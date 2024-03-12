package com.algaworks.algafood.api.assembler;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.input.RestauranteInput;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.infraestructure.configuration.Mapper;

@Component
public class RestauranteInputDisassembler extends EntityInputDisassembler<RestauranteInput, Restaurante> {

	public RestauranteInputDisassembler(Mapper mapper) {
		super(mapper);
	}
}

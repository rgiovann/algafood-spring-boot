package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class EntityInputDisassembler {

	//@Autowired
	private final ModelMapper modelMapper;

	public EntityInputDisassembler(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	public <T, U> U  toEntity(T source, Class<U> targetClass) {
	    return modelMapper.map(source, targetClass);
	}
	
	
}
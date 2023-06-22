package com.algaworks.algafood.infraestructure.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.api.dto.EnderecoDto;
import com.algaworks.algafood.domain.model.Endereco;

@Configuration
public class ModelMapperConfig {

    @Bean
    ModelMapper modelMapper() {
    	
    	var modelMapper = new ModelMapper(); 
    	
    	var enderecoToEnderecoDtoTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoDto.class);
    	
    	enderecoToEnderecoDtoTypeMap.<String>addMapping(
    			enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(), 
    			(enderecoDtoDest,value) -> enderecoDtoDest.getCidade().setEstado(value) );
    	    	
        return modelMapper;
    }

}

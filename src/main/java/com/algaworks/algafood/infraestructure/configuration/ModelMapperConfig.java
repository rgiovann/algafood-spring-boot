package com.algaworks.algafood.infraestructure.configuration;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.api.dto.EnderecoDto;
import com.algaworks.algafood.api.input.CidadeIdInput;
import com.algaworks.algafood.api.input.CozinhaIdInput;
import com.algaworks.algafood.api.input.EstadoIdInput;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Endereco;
import com.algaworks.algafood.domain.model.Estado;

@Configuration
public class ModelMapperConfig {

    @Bean
    ModelMapper modelMapper() {
    	
    	var modelMapper = new ModelMapper(); 
    	
    	var enderecoToEnderecoDtoTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoDto.class);
    	
    	enderecoToEnderecoDtoTypeMap.<String>addMapping(
    			enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(), 
    			(enderecoDtoDest,value) -> enderecoDtoDest.getCidade().setEstado(value) );
    	    	
        
    	Converter<EstadoIdInput, Estado> estadoConverter = new AbstractConverter<>() {
    		@Override
    		protected Estado convert(EstadoIdInput source) {
    			Estado estado = new Estado();
    			estado.setId(source.getId());
    			return estado;
    		}
    	};
    	
    	
    	Converter<CozinhaIdInput, Cozinha> cozinhaConverter = new AbstractConverter<>() {
    		@Override
    		protected Cozinha convert(CozinhaIdInput source) {
    			Cozinha cozinha = new Cozinha();
    			cozinha.setId(source.getId());
    			return cozinha;
    		}
    	};

    	Converter<CidadeIdInput, Cidade> cidadeConverter = new AbstractConverter<>() {
    		@Override
    		protected Cidade convert(CidadeIdInput source) {
    			Cidade cidade = new Cidade();
    			cidade.setId(source.getId());
    			return cidade;
    		}
    	};
    	
    	
    	modelMapper.addConverter(estadoConverter, EstadoIdInput.class, Estado.class);
    	modelMapper.addConverter(cozinhaConverter, CozinhaIdInput.class, Cozinha.class);
    	modelMapper.addConverter(cidadeConverter, CidadeIdInput.class, Cidade.class);
    	
    	
    	return modelMapper;
        
        
        
    }

}

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
import com.algaworks.algafood.api.input.FormaPagamentoIdInput;
import com.algaworks.algafood.api.input.ProdutoIdInput;
import com.algaworks.algafood.api.input.RestauranteIdInput;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Endereco;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;

@Configuration
public class ModelMapperConfig {

    @Bean
    Mapper mapper() {
    	
    	final  var modelMapper = new ModelMapper(); 
    	
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
    	
    	Converter<RestauranteIdInput, Restaurante> restauranteConverter = new AbstractConverter<>() {
    		@Override
    		protected Restaurante convert(RestauranteIdInput source) {
    			Restaurante restaurante = new Restaurante();
    			restaurante.setId(source.getId());
    			return restaurante;
    		}
    	};
    	
    	Converter<FormaPagamentoIdInput, FormaPagamento> formaPagamentoConverter = new AbstractConverter<>() {
    		@Override
    		protected FormaPagamento convert(FormaPagamentoIdInput source) {
    			FormaPagamento formaPagto = new FormaPagamento();
    			formaPagto.setId(source.getId());
    			return formaPagto;
    		}
    	};
    	
    	
    	Converter<ProdutoIdInput, Produto> produtoConverter = new AbstractConverter<>() {
    		@Override
    		protected Produto convert(ProdutoIdInput source) {
    			Produto produto = new Produto();
    			produto.setId(source.getId());
    			return produto;
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
    	
    	modelMapper.addConverter(restauranteConverter, RestauranteIdInput.class, Restaurante.class);
    	modelMapper.addConverter(formaPagamentoConverter, FormaPagamentoIdInput.class, FormaPagamento.class);
    	modelMapper.addConverter(produtoConverter, ProdutoIdInput.class, Produto.class);

    	modelMapper.addConverter(estadoConverter, EstadoIdInput.class, Estado.class);
    	modelMapper.addConverter(cozinhaConverter, CozinhaIdInput.class, Cozinha.class);
    	modelMapper.addConverter(cidadeConverter, CidadeIdInput.class, Cidade.class);
    	
    	
    	return new Mapper() {
            @Override
            public <D> D map(Object source, Class<D> destinationType) {
                return modelMapper.map(source, destinationType);
            }

            @Override
            public void map(Object source, Object destination) {
                modelMapper.map(source, destination);
            }
        };     
    }

}

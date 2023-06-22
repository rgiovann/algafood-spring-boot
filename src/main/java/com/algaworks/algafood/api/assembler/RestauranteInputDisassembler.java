package com.algaworks.algafood.api.assembler;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.input.CidadeIdInput;
import com.algaworks.algafood.api.input.CozinhaIdInput;
import com.algaworks.algafood.api.input.RestauranteInput;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteInputDisassembler extends EntityInputDisassembler<RestauranteInput, Restaurante>{

	public RestauranteInputDisassembler(ModelMapper mapper) {
		super(mapper);
	}
	
	@Override
	public void copyToEntity(RestauranteInput restauranteInput, Restaurante restaurante) {

		// conversor para CozinhIdInput
        Converter<CozinhaIdInput, Cozinha> cozinhaConverter = new Converter<CozinhaIdInput, Cozinha>() {
            @Override
            public Cozinha convert(MappingContext<CozinhaIdInput, Cozinha> context) {
            	Cozinha cozinha = new Cozinha(); 
            	cozinha.setId(context.getSource().getId());
                return cozinha;
            }
        };
        
        // conversor para CidadeIdInput
        Converter<CidadeIdInput, Cidade> cidadeConverter = new Converter<CidadeIdInput, Cidade>() {
            @Override
            public Cidade convert(MappingContext<CidadeIdInput, Cidade> context) {
            	Cidade cidade = new Cidade(); 
            	cidade.setId(context.getSource().getId());
                return cidade;
            }
        };
        
        // adicina os conversores ao bean modelMapper
        mapper.addConverter(cozinhaConverter, CozinhaIdInput.class, Cozinha.class);
        mapper.addConverter(cidadeConverter,   CidadeIdInput.class, Cidade.class);

        
        mapper.map(restauranteInput,restaurante );
		
	}
}

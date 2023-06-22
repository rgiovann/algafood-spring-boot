package com.algaworks.algafood.api.assembler;

import org.modelmapper.AbstractConverter;
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
public class RestauranteInputDisassembler extends EntityInputDisassembler<RestauranteInput, Restaurante> {

	public RestauranteInputDisassembler(ModelMapper mapper) {
		super(mapper);
	}

	private Converter<CozinhaIdInput, Cozinha> cozinhaConverter = new AbstractConverter<>() {
		@Override
		protected Cozinha convert(CozinhaIdInput source) {
			Cozinha cozinha = new Cozinha();
			System.out.println("======================= AQUI - COZINHA ===================");
			cozinha.setId(source.getId());
			return cozinha;
		}
	};

	private Converter<CidadeIdInput, Cidade> cidadeConverter = new AbstractConverter<>() {
		@Override
		protected Cidade convert(CidadeIdInput source) {
			Cidade cidade = new Cidade();
			System.out.println("======================= AQUI - CIDADE ===================");
			cidade.setId(source.getId());
			return cidade;
		}
	};
	
	@Override
	public void copyToEntity(RestauranteInput restauranteInput, Restaurante restaurante) {

		// adiciona os conversores ao bean modelMapper
		System.out.println("======================= AQUI - Add cozinhaConverter ===================");
		mapper.addConverter(cozinhaConverter, CozinhaIdInput.class, Cozinha.class);
		System.out.println("======================= AQUI - Add cidadeConverter ===================");
		mapper.addConverter(cidadeConverter, CidadeIdInput.class, Cidade.class);
		mapper.map(restauranteInput, restaurante);

	}


}

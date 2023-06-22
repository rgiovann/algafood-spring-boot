package com.algaworks.algafood.api.assembler;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.input.CidadeIdInput;
import com.algaworks.algafood.api.input.CidadeInput;
import com.algaworks.algafood.api.input.EstadoIdInput;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;

@Component
public class CidadeInputDisassembler extends EntityInputDisassembler<CidadeInput, Cidade>{

	public CidadeInputDisassembler(ModelMapper mapper) {
		super(mapper);
	}
	
	private Converter<EstadoIdInput, Estado> estadoConverter = new AbstractConverter<>() {
		@Override
		protected Estado convert(EstadoIdInput source) {
			Estado estado = new Estado();
			estado.setId(source.getId());
			System.out.println("======================= AQUI - ESTADO ===================");
			return estado;
		}
	};
	
	@Override
	public void copyToEntity(CidadeInput cidadeInput, Cidade cidade) {

        mapper.addConverter(estadoConverter, EstadoIdInput.class, Estado.class);
        
        mapper.map(cidadeInput,cidade );
		
	}
	
}

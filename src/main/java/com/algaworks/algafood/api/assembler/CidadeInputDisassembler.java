package com.algaworks.algafood.api.assembler;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.input.CidadeInput;
import com.algaworks.algafood.api.input.EstadoIdInput;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;

@Component
public class CidadeInputDisassembler extends EntityInputDisassembler<CidadeInput, Cidade>{

	public CidadeInputDisassembler(ModelMapper mapper) {
		super(mapper);
	}
	
	@Override
	public void copyToEntity(CidadeInput cidadeInput, Cidade cidade) {

        Converter<EstadoIdInput, Estado> estadoConverter = new Converter<EstadoIdInput, Estado>() {
            @Override
            public Estado convert(MappingContext<EstadoIdInput, Estado> context) {
            	Estado estado = new Estado(); 
            	estado.setId(context.getSource().getId());
                return estado;
            }
        };

        mapper.addConverter(estadoConverter, EstadoIdInput.class, Estado.class);
        
        mapper.map(cidadeInput,cidade );
		
	}
}

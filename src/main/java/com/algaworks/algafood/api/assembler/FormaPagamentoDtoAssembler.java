package com.algaworks.algafood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Arrays;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.FormaPagamentoController;
import com.algaworks.algafood.api.dto.FormaPagamentoDto;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.infraestructure.configuration.Mapper;

 

@Component
public class FormaPagamentoDtoAssembler extends EntitytDtoAssembler<FormaPagamentoDto, 
                                                                    FormaPagamento,
                                                                    FormaPagamentoController>{

	public FormaPagamentoDtoAssembler(Mapper mapper) {
		super(mapper,FormaPagamentoController.class,FormaPagamentoDto.class );
	}

	@Override
	public List<Link> constructLinks(FormaPagamento entityObject) {
		// TODO Auto-generated method stub
		return 				Arrays.asList(
				linkTo(FormaPagamentoController.class).withRel("formas de pagamento"),
				linkTo( methodOn(FormaPagamentoController.class).buscar(entityObject.getId(),null)).withSelfRel()	
				//linkTo(FormaPagamentoController.class).withRel(IanaLinkRelations.SELF.value())
				);
	}

	@Override
	public Link constructCollectionLink() {
 		return linkTo(FormaPagamentoController.class).withSelfRel();

	}

 

}
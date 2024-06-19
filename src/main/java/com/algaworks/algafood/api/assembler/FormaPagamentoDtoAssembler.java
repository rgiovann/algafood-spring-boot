package com.algaworks.algafood.api.assembler;

import java.util.Arrays;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.FormaPagamentoController;
import com.algaworks.algafood.api.dto.FormaPagamentoDto;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.infraestructure.configuration.Mapper;

 

@Component
public class FormaPagamentoDtoAssembler extends EntitytDtoAssembler<FormaPagamentoDto, 
                                                                    FormaPagamento,
                                                                    FormaPagamentoController>{

    private AlgaLinks formaPagamentoLinks;

	public FormaPagamentoDtoAssembler(Mapper mapper,AlgaLinks formaPagamentoLinks) {
		super(mapper,FormaPagamentoController.class,FormaPagamentoDto.class );
		this.formaPagamentoLinks = formaPagamentoLinks;
	}

	@Override
	public List<Link> constructLinks(FormaPagamento entityObject) {
		return 				Arrays.asList(
				this.formaPagamentoLinks.linkToFormaPagamento(entityObject.getId()),
				this.formaPagamentoLinks.linkToFormaPagamento("formas de pagamento")
 				);
	}

	@Override
	public Link constructCollectionLink() {
 		return this.formaPagamentoLinks.linkToFormaPagamento();

	}

 

}
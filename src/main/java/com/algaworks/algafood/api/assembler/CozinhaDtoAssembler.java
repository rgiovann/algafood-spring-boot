package com.algaworks.algafood.api.assembler;

import java.util.Arrays;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.CozinhaController;
import com.algaworks.algafood.api.dto.CozinhaDto;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.infraestructure.configuration.Mapper;

 

@Component
public class CozinhaDtoAssembler extends EntitytDtoAssembler<CozinhaDto,Cozinha,CozinhaController>{

	private AlgaLinks cozinhaLinks;
	public CozinhaDtoAssembler(Mapper mapper,AlgaLinks cozinhaLinks) {
		super(mapper,CozinhaController.class,CozinhaDto.class);
		this.cozinhaLinks = cozinhaLinks;
	}

	@Override
	public  List<Link> constructLinks(Cozinha cozinha ) {
		return  
				Arrays.asList(
						cozinhaLinks.linkToCozinha(cozinha.getId()),
						cozinhaLinks.linkToCozinha("cozinhas")
						);
	}

	@Override
	public Link constructCollectionLink() {
 		return cozinhaLinks.linkToCozinha();
	}


}
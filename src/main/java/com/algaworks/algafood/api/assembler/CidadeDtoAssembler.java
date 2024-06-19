package com.algaworks.algafood.api.assembler;

import java.util.Arrays;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.CidadeController;
import com.algaworks.algafood.api.dto.CidadeDto;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.infraestructure.configuration.Mapper;

 

@Component
public class CidadeDtoAssembler extends EntitytDtoAssembler<CidadeDto,Cidade,CidadeController>{

	private AlgaLinks cidadeLinks;

	public CidadeDtoAssembler(Mapper mapper,AlgaLinks cidadeLinks) {
		super(mapper,CidadeController.class,CidadeDto.class);
		this.cidadeLinks = cidadeLinks;

	}

	@Override
	public List<Link> constructLinks(Cidade entityObject) {
		return Arrays.asList(cidadeLinks.linkToCidade(entityObject.getId()),
							 cidadeLinks.linkToCidade("cidades"),
							 cidadeLinks.linkToEstado(entityObject.getEstado().getId()),
							 cidadeLinks.linkToEstado("estados")

	);
	}

	@Override
	public  Link  constructCollectionLink( ) {
 		return cidadeLinks.linkToCidade();
	}
	

}
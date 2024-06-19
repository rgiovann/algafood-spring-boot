package com.algaworks.algafood.api.assembler;


import java.util.Arrays;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.GrupoController;
import com.algaworks.algafood.api.dto.GrupoDto;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.infraestructure.configuration.Mapper;

@Component
public class GrupoDtoAssembler extends EntitytDtoAssembler<GrupoDto, Grupo, GrupoController >{

	private AlgaLinks grupoLinks;

	public GrupoDtoAssembler(Mapper mapper,AlgaLinks grupoLinks) {
 		super(mapper,GrupoController.class,GrupoDto.class);
 		this.grupoLinks = grupoLinks;
	}
	
	@Override
	public List<Link> constructLinks(Grupo entityObject) {
		// TODO Auto-generated method stub
		return Arrays.asList(grupoLinks.linkToGrupo(entityObject.getId()),
							 grupoLinks.linkToGrupo("grupos"));              
	
	}

	@Override
	public Link constructCollectionLink() {
 		return grupoLinks.linkToGrupo();
 	}	
 }
 
 
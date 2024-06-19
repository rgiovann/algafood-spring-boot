package com.algaworks.algafood.api.assembler;

import java.util.Arrays;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.UsuarioController;
import com.algaworks.algafood.api.dto.UsuarioDto;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.infraestructure.configuration.Mapper;

 

@Component
public class UsuarioDtoAssembler extends EntitytDtoAssembler<UsuarioDto, Usuario,UsuarioController >{

	private AlgaLinks usuarioLinks;

	public UsuarioDtoAssembler(Mapper mapper, AlgaLinks usuarioLinks ) {
		super(mapper,UsuarioController.class,UsuarioDto.class);
		this.usuarioLinks = usuarioLinks;
	}

	@Override
	public List<Link> constructLinks(Usuario entityObject) {
		return 					Arrays.asList( usuarioLinks.linkToUsuario(entityObject.getId()),
											   usuarioLinks.linkToUsuario("usuarios"),
											   usuarioLinks.linkToGrupoUsuario(entityObject.getId())

	     );
	}

	@Override
	public Link constructCollectionLink() {
 		return usuarioLinks.linkToUsuario();

	}
	
}



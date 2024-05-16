package com.algaworks.algafood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Arrays;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.UsuarioController;
import com.algaworks.algafood.api.controller.UsuarioGrupoController;

import com.algaworks.algafood.api.dto.UsuarioDto;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.infraestructure.configuration.Mapper;

 

@Component
public class UsuarioDtoAssembler extends EntitytDtoAssembler<UsuarioDto, Usuario,UsuarioController >{

	public UsuarioDtoAssembler(Mapper mapper ) {
		super(mapper,UsuarioController.class,UsuarioDto.class);
//				Arrays.asList(linkTo( methodOn(UsuarioController.class).buscar(usuarioDto.getId()))
//		                              .withSelfRel(),
//		        	          linkTo( methodOn(UsuarioController.class).listar())
//			    		               .withRel(("usuarios")),
//			    		      linkTo( methodOn(UsuarioGrupoController.class).listarPermissoes( usuarioDto.getId()))
//			                              .withRel("grupos-usuarios"));,

      //				linkTo(CidadeController.class).withSelfRel());
	}

	@Override
	public List<Link> constructLinks(Usuario entityObject) {
		return 					Arrays.asList(linkTo( methodOn(UsuarioController.class).buscar(entityObject.getId()))
                .withSelfRel(),
       linkTo( methodOn(UsuarioController.class).listar())
	               .withRel(("usuarios")),
	      linkTo( methodOn(UsuarioGrupoController.class).listarPermissoes( entityObject.getId()))
                    .withRel("grupos-usuarios"));
	}
	
	}



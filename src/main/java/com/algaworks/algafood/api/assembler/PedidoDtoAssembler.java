package com.algaworks.algafood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Arrays;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.dto.PedidoDto;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.infraestructure.configuration.Mapper;

 @Component
public class PedidoDtoAssembler extends EntitytDtoAssembler<PedidoDto, Pedido, PedidoController>{

		public PedidoDtoAssembler(Mapper mapper ) {
			super(mapper,PedidoController.class,PedidoDto.class);
	 	}

		@Override
		public List<Link> constructLinks(Pedido entityObject) {
			// TODO Auto-generated method stub
			return 	Arrays.asList(linkTo( methodOn(PedidoController.class).buscar(entityObject.getCodigo()))
                     .withSelfRel(),
                     linkTo( methodOn(PedidoController.class).pesquisar(null, null))
		             .withRel(("cidades")),
		             linkTo( methodOn(RestauranteController.class).buscar(entityObject.getRestaurante().getId()))
                     .withRel("restaurantes")			    		               
		);
		}


}
 
 /*
 @Component
 public class CidadeDtoAssembler extends EntitytDtoAssembler<CidadeDto,Cidade>{

 	public PedidoDtoAssembler(Mapper mapper,PedidoDto dtoObject) {
 		super(mapper,PedidoDto.class,
 				Arrays.asList(linkTo( methodOn(PedidoController.class).buscar(dtoObject.getId()))
 		                              .withSelfRel(),
 		        	          linkTo( methodOn(PedidoController.class).listar())
 			    		               .withRel(("cidades")),
 			    		      linkTo( methodOn(RestauranteController.class).buscar(dtoObject.getEstado().getId()))
 			                              .withSelfRel()			    		               
 						),
 				linkTo(CidadeController.class).withSelfRel());
 	}

 }
 */
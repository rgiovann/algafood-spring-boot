package com.algaworks.algafood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.CidadeController;
import com.algaworks.algafood.api.controller.FormaPagamentoController;
import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.controller.RestauranteProdutoController;
import com.algaworks.algafood.api.controller.UsuarioController;
import com.algaworks.algafood.api.dto.PedidoDto;
import com.algaworks.algafood.domain.model.ItemPedido;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.infraestructure.configuration.Mapper;

 @Component
public class PedidoDtoAssembler extends EntitytDtoAssembler<PedidoDto, Pedido, PedidoController>{

		public PedidoDtoAssembler(Mapper mapper ) {
			super(mapper,PedidoController.class,PedidoDto.class);
	 	}

		@Override
		public List<Link> constructLinks(Pedido entityObject) {
			Long cidadePedidoId = entityObject.getEnderecoEntrega().getCidade().getId();
			Long formaPagamentoId = entityObject.getFormaPagamento().getId();
			Long restauranteId = entityObject.getRestaurante().getId();

			/*List<Link> listaDeLinks = 	Arrays.asList(linkTo( methodOn(PedidoController.class).buscar(entityObject.getCodigo()))
                     .withSelfRel(),
                     linkTo( methodOn(CidadeController.class).buscar( cidadePedidoId) )
		             .withSelfRel(),
	                 linkTo( methodOn(FormaPagamentoController.class).buscar(formaPagamentoId, null) )
			         .withSelfRel(),		             
		             linkTo( methodOn(RestauranteController.class).buscar(restauranteId))
                     .withSelfRel()                 
		);*/
			
		    // Create a modifiable list
	    List<Link> listaDeLinks = new ArrayList<>();

	        // Add initial links
	    listaDeLinks.add(linkTo(methodOn(PedidoController.class).buscar(entityObject.getCodigo())).withSelfRel());
	    listaDeLinks.add(linkTo(methodOn(CidadeController.class).buscar(cidadePedidoId)).withSelfRel());
	    listaDeLinks.add(linkTo(methodOn(FormaPagamentoController.class).buscar(formaPagamentoId, null)).withSelfRel());
	    listaDeLinks.add(linkTo(methodOn(RestauranteController.class).buscar(restauranteId)).withSelfRel());
		listaDeLinks.add(linkTo( methodOn(UsuarioController.class).buscar(entityObject.getCliente().getId()))
                .withSelfRel());	
			
		List<ItemPedido> itens = entityObject.getItens();
		for (ItemPedido itemPedido : itens) {
			Long pedidoId = itemPedido.getProduto().getId();
			listaDeLinks.add( linkTo( methodOn(RestauranteProdutoController.class).buscarProduto(restauranteId,pedidoId))
                    .withSelfRel() );
			}  
		listaDeLinks.add(linkTo(PedidoController.class).withRel("pedidos"));
		
		return listaDeLinks;	
		}

		@Override
		public Link constructCollectionLink() {
			System.out.println((linkTo(PedidoController.class).withSelfRel().toString()));
	 		return linkTo(PedidoController.class).withSelfRel();
		}


}

 
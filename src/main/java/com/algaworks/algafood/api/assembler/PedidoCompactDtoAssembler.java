package com.algaworks.algafood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.dto.PedidoCompactDto;
import com.algaworks.algafood.domain.model.ItemPedido;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.infraestructure.configuration.Mapper;

  
 @Component
 public class PedidoCompactDtoAssembler extends EntitytDtoAssembler<PedidoCompactDto,Pedido,
                                                                   PedidoController>{
    private AlgaLinks pedidoLinks;
    
 	public PedidoCompactDtoAssembler(Mapper mapper,AlgaLinks pedidoLinks) {
		super(mapper,PedidoController.class, PedidoCompactDto.class);
		this.pedidoLinks = pedidoLinks;
	}

	@Override
	public List<Link> constructLinks(Pedido entityObject) {
		Long restauranteId = entityObject.getRestaurante().getId();
        Long usarioId   = entityObject.getCliente().getId();
	   
        // Create a modifiable list
		List<Link> listaDeLinks = new ArrayList<>();

	     // Add initial links
	    listaDeLinks.add( this.pedidoLinks.linkToPedidos(entityObject));
	    listaDeLinks.add( this.pedidoLinks.linkToRestaurante(restauranteId));
		listaDeLinks.add( this.pedidoLinks.linkToUsuario(usarioId));
 			
		List<ItemPedido> itens = entityObject.getItens();
		for (ItemPedido itemPedido : itens) {
			Long pedidoId = itemPedido.getProduto().getId();
			listaDeLinks.add(this.pedidoLinks.linkToProduto(restauranteId, pedidoId));
			}  
		listaDeLinks.add( this.pedidoLinks.linkToPedidos("pedidos"));
		
		return listaDeLinks;	

	}

	@Override
	public Link constructCollectionLink() {
 		return linkTo(PedidoController.class).withSelfRel();
 	}

 

}
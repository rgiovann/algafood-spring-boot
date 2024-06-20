package com.algaworks.algafood.api.assembler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.dto.PedidoDto;
import com.algaworks.algafood.domain.model.ItemPedido;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.infraestructure.configuration.Mapper;

 @Component
public class PedidoDtoAssembler extends EntitytDtoAssembler<PedidoDto, Pedido, PedidoController>{

	    private AlgaLinks pedidoLinks;

		public PedidoDtoAssembler(Mapper mapper,AlgaLinks pedidoLinks ) {
			super(mapper,PedidoController.class,PedidoDto.class);
			this.pedidoLinks = pedidoLinks;

	 	}

		@Override
		public List<Link> constructLinks(Pedido entityObject) {
			Long cidadePedidoId = entityObject.getEnderecoEntrega().getCidade().getId();
			Long formaPagamentoId = entityObject.getFormaPagamento().getId();
			Long restauranteId = entityObject.getRestaurante().getId();
            Long usarioId   = entityObject.getCliente().getId();
			 
		    // Create a modifiable list
		    List<Link> listaDeLinks = new ArrayList<>();
	
		    // Add initial links
		    listaDeLinks.add(this.pedidoLinks.linkToPedidos(entityObject) );
		    listaDeLinks.add(this.pedidoLinks.linkToCidade(cidadePedidoId));
		    listaDeLinks.add(this.pedidoLinks.linkToFormaPagamento(formaPagamentoId));
		    listaDeLinks.add(this.pedidoLinks.linkToRestaurante(restauranteId));
			listaDeLinks.add(this.pedidoLinks.linkToUsuario(usarioId));	
			
			if(entityObject.podeSerConfirmado()) {
				listaDeLinks.add(this.pedidoLinks.linkToConfirmacaoPedido(entityObject.getCodigo(), "confirmar"));
			}
			
			if(entityObject.podeSerCancelado()) {
				listaDeLinks.add(this.pedidoLinks.linkToCancelamentoPedido(entityObject.getCodigo(), "cancelar"));
			}
			
			if(entityObject.podeSerEntregue()) {
				listaDeLinks.add(this.pedidoLinks.linkToEntregaPedido(entityObject.getCodigo(), "entregar"));
			}

			List<ItemPedido> itens = entityObject.getItens();
			for (ItemPedido itemPedido : itens) {
				Long pedidoId = itemPedido.getProduto().getId();
				listaDeLinks.add( this.pedidoLinks.linkToProduto(restauranteId,pedidoId));
				}  
 			listaDeLinks.add(this.pedidoLinks.linkToPedidos("pedidos") );
 			return listaDeLinks;	
		}

		@Override
		public Link constructCollectionLink() {
			//System.out.println((linkTo(PedidoController.class).withSelfRel().toString()));
	 		return this.pedidoLinks.linkToPedidos("pedidos");
		}


}

 
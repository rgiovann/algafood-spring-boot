package com.algaworks.algafood.api;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.CidadeController;
import com.algaworks.algafood.api.controller.CozinhaController;
import com.algaworks.algafood.api.controller.EstadoController;
import com.algaworks.algafood.api.controller.FluxoPedidoController;
import com.algaworks.algafood.api.controller.FormaPagamentoController;
import com.algaworks.algafood.api.controller.GrupoController;
import com.algaworks.algafood.api.controller.GrupoPermissaoController;
import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.controller.RestauranteProdutoController;
import com.algaworks.algafood.api.controller.RestauranteUsuarioResponsavelController;
import com.algaworks.algafood.api.controller.UsuarioController;
import com.algaworks.algafood.api.controller.UsuarioGrupoController;
import com.algaworks.algafood.api.dto.UsuarioDto;
import com.algaworks.algafood.domain.model.Pedido;

@Component
public class AlgaLinks {
	
	
	public static final TemplateVariables PAGINACAO_VARIABLES = new TemplateVariables(
			new TemplateVariable("page",VariableType.REQUEST_PARAM),
			new TemplateVariable("size",VariableType.REQUEST_PARAM),
			new TemplateVariable("sort",VariableType.REQUEST_PARAM)

			);	
	public Link linkToPedidos(String rel) {

		TemplateVariables filtroVariables = new TemplateVariables(
				new TemplateVariable("restauranteNome",VariableType.REQUEST_PARAM),
				new TemplateVariable("clienteId",VariableType.REQUEST_PARAM),
				new TemplateVariable("restauranteId",VariableType.REQUEST_PARAM),
				new TemplateVariable("dataCriacaoInicio",VariableType.REQUEST_PARAM),
				new TemplateVariable("dataCriacaoFim",VariableType.REQUEST_PARAM)

				);
		String pedidosURL = linkTo(PedidoController.class).toUri().toString();
		return Link.of(UriTemplate.of(pedidosURL,PAGINACAO_VARIABLES.concat(filtroVariables)),rel);			
	}
	
	public Link linkToPedidos(Pedido entityObject ) {
		return linkTo(methodOn(PedidoController.class).buscar(entityObject.getCodigo())).withSelfRel();
	}
	
	public Link linkToConfirmacaoPedido(String codigoPedido, String rel) {
		return linkTo(methodOn(FluxoPedidoController.class).confirmarPedido(codigoPedido))
				.withRel(rel);
		
	}
	
	public Link linkToEntregaPedido(String codigoPedido, String rel) {
		return linkTo(methodOn(FluxoPedidoController.class).entregarPedido(codigoPedido))
				.withRel(rel);
		
	}
	
	public Link linkToCancelamentoPedido(String codigoPedido, String rel) {
		return linkTo(methodOn(FluxoPedidoController.class).cancelarPedido(codigoPedido))
				.withRel(rel);
		
	}


	
	public Link linkToProduto(Long restauranteId,Long pedidoId) {
	     return linkTo( methodOn(RestauranteProdutoController.class).buscarProduto(restauranteId,pedidoId))
	               .withSelfRel();   
	   }
	
	public Link linkToProduto(Long restauranteId) {
 		return linkTo(methodOn(RestauranteProdutoController.class).listarProdutos(restauranteId,false)).withSelfRel();
	}
	
	public Link linkToRestaurante(Long restauranteId) {
		return linkTo( methodOn(CidadeController.class).buscar(restauranteId))
                .withSelfRel();
		
	}
	
	public Link linkToRestaurante(String rel) {
		return linkTo(methodOn(RestauranteController.class).listar()).withRel(rel);	
	}
	
	public Link linkToRestaurante() {
		return linkTo(RestauranteController.class).withSelfRel();
	}

	public Link linkToFormaPagamento(Long formaPagamentoId) {
		return linkTo(methodOn(FormaPagamentoController.class).buscar(formaPagamentoId, null)).withSelfRel();
	}
		
	public Link linkToFormaPagamento(String rel) {
			return linkTo(methodOn(FormaPagamentoController.class).listar(null)).withRel(rel);
	}
	
	public Link linkToFormaPagamento( ) {
		return linkTo(FormaPagamentoController.class).withSelfRel();		
}
	
	public Link linkToCidade(Long cidadeId) {
		return linkTo(methodOn(CidadeController.class).buscar(cidadeId)).withSelfRel();
	}
	
	public Link linkToCidade(String rel) {
		return linkTo(methodOn(CidadeController.class).listar()).withRel(rel);
}
	
	public Link linkToCidade() {
		return linkTo(CidadeController.class).withSelfRel();		
	}
	
	public Link linkToEstado(Long estadoId) {
		return linkTo(methodOn(EstadoController.class).buscar(estadoId)).withSelfRel();
	}
	
	public Link linkToEstado(String rel) {
		return linkTo(methodOn(EstadoController.class).listar()).withRel(rel);
}
	
	public Link linkToEstado() {
		return linkTo(EstadoController.class).withSelfRel();		
	}
	  
   
   public Link linkToCozinha(Long cozinhaId) {
	   return linkTo( methodOn(CozinhaController.class).buscar(cozinhaId)).withSelfRel();
   }
   
   public Link linkToCozinha(String rel) {
		String pedidosURL = linkTo(CozinhaController.class).toUri().toString();
	   return Link.of(UriTemplate.of(pedidosURL,PAGINACAO_VARIABLES),rel);		
    }
   
   public Link linkToCozinha()
   {
	   return linkTo(CozinhaController.class).withSelfRel();
   }
   
   
   public Link linkToUsuario(Long usuarioId) {
	   return linkTo( methodOn(UsuarioController.class).buscar(usuarioId)).withSelfRel();
   }
   
   public Link linkToUsuario(String rel) {
		return linkTo(methodOn(UsuarioController.class).listar()).withRel(rel);
    }
   
   public Link linkToGrupoUsuario(Long usuarioId)
   {
	   return linkTo( methodOn(UsuarioGrupoController.class).listarGrupos(usuarioId))
       .withRel("grupos-usuarios");   }
   
   public Link linkToUsuario()
   {
	   return linkTo(UsuarioController.class).withSelfRel();
   }
      
   public void setRestaurantUserResponsibleLink (CollectionModel<UsuarioDto> collectionUsuarioDto,Long restauranteId){
		collectionUsuarioDto.removeLinks();
		collectionUsuarioDto.add(linkTo(methodOn(RestauranteUsuarioResponsavelController.class)
				.listar(restauranteId)).withSelfRel());
		return;
	}

   
    public Link linkToGrupo(Long grupoId) {
    	return linkTo( methodOn(GrupoController.class).buscar(grupoId)).withSelfRel();
    }
    
    public Link linkToGrupo(String rel) {
    	return  linkTo( methodOn(GrupoController.class).listar()).withRel(rel); 
    }
    
 	public Link linkToGrupo() {
		return linkTo(GrupoController.class).withSelfRel();
	}	
	
   public Link linkToPermissoes(Long groupId) {
	   return linkTo(methodOn(GrupoPermissaoController.class).listarPermissoes(groupId)).withSelfRel();
   }
   
}

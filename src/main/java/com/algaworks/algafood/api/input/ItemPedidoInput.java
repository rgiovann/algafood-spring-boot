package com.algaworks.algafood.api.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoInput {
 
	@NotNull
	private Integer quantidade;
	
	private String  observacao;
	
	@NotNull
 	private ProdutoIdInput produto;

}

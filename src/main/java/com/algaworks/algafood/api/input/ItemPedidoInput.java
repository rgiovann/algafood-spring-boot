package com.algaworks.algafood.api.input;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoInput {
 
	@NotNull
	@PositiveOrZero
	private Integer quantidade;
	
	private String  observacao;
	
	@Valid
	@NotNull
 	private ProdutoIdInput produto;

}

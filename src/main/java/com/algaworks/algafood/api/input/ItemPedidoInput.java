package com.algaworks.algafood.api.input;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoInput {
 
	@NotNull
	@PositiveOrZero
	@ApiModelProperty(value="Quantidade do item de pedido",example="1",required=true )   
	private Integer quantidade;
	
	@ApiModelProperty(value="Observação do item de pedido",example="Sem cebola")   
	private String  observacao;
	
	@Valid
	@NotNull
 	private ProdutoIdInput produto;

}

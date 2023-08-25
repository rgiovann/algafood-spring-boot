package com.algaworks.algafood.api.dto;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "Item de Pedido", description = "Representa um item de pedido")

public class ItemPedidoDto {
	
	@ApiModelProperty(value="Id item de pedido",example="1"  )   
   	private Long id;
   	private ProdutoCompactDto produto;
	private Integer quantidade;
  	private BigDecimal precoTotal;
	private String     observacao;

}

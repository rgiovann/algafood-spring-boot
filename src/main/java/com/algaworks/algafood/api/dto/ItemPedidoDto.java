package com.algaworks.algafood.api.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoDto {
	
   	private Long id;
   	private ProdutoCompactDto produto;
	private Integer quantidade;
  	private BigDecimal precoTotal;
	private String     observacao;

}

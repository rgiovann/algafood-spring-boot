package com.algaworks.algafood.api.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoCompactDto {
	
	private Long id;
	private String nome;
	private BigDecimal preco;
	
}

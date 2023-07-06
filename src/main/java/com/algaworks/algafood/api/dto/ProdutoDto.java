package com.algaworks.algafood.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoDto extends ProdutoCompactDto {
	
  
	private String descricao;
	private Boolean ativo;
	
}

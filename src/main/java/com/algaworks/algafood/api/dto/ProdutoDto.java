package com.algaworks.algafood.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoDto extends ProdutoCompactDto {
	
  
	@ApiModelProperty(example = "Acompanha farinha, mandioca e vinagrete")
	private String descricao;
	@ApiModelProperty(example = "true")
	private Boolean ativo;
	
}

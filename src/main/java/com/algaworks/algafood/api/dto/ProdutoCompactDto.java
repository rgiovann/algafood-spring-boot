package com.algaworks.algafood.api.dto;

import java.math.BigDecimal;

import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Relation(collectionRelation = "produtos")
@ApiModel(value = "Produto", description = "Representa um produto")

public class ProdutoCompactDto {
	
	@ApiModelProperty(value="Id do produto",example="1" )   
	private Long id;
	@ApiModelProperty(value="Nome do produto",example="X-Picanha" )   
	private String nome;
	@ApiModelProperty(value="Preço do produto",example="32.49" )   
	private BigDecimal preco;
	
}

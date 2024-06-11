package com.algaworks.algafood.api.dto;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Relation(collectionRelation = "formas de pagamento")
@ApiModel(value = "Forma de Pagamento", description = "Representa a forma de pagamento")

public class FormaPagamentoDto extends RepresentationModel<FormaPagamentoDto>
							   {

	@ApiModelProperty(value="Id da forma de pagamento",example="1")
	private Long id;
	
	@ApiModelProperty(value="Descrição da forma de pagamento",example="Cartão de débito")
	private String descricao;
	
}

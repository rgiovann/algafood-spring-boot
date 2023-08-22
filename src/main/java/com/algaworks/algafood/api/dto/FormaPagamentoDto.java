package com.algaworks.algafood.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormaPagamentoDto {

	@ApiModelProperty(value="Id da forma de pagamento",example="1")
	private Long id;
	
	@ApiModelProperty(value="Descrição da forma de pagamento",example="Cartão de débito")
	private String descricao;
	
}

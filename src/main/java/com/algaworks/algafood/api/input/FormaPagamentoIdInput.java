package com.algaworks.algafood.api.input;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormaPagamentoIdInput {
	
	@NotNull
	@ApiModelProperty(value="Id da forma de Pagamento",example="1" )   
	private Long id;
}

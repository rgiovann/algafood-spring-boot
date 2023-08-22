package com.algaworks.algafood.api.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormaPagamentoNomeInput {
	
	@NotBlank
	@ApiModelProperty(value="Nome da forma de Ppagameto",example="Cartão de débito",required=true)  // criar plugin "not blank"	
	private String descricao;

}

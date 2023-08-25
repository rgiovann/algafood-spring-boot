package com.algaworks.algafood.api.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

 
@Setter
@Getter
public class EnderecoInput {

	@ApiModelProperty(value="CEP do restaurante",example="89050-000",required=true)  // criar plugin "not blank"
	@NotBlank
	private String cep;

	@ApiModelProperty(value="Logradouro do restaurante",example="Av. 7 de setembro",required=true)  // criar plugin "not blank"
	@NotBlank
	private String logradouro;
	
	@ApiModelProperty(value="Número do restaurante",example="3500",required=true)  // criar plugin "not blank"
	@NotBlank
	private String numero;
	
	@ApiModelProperty(value="Complemento endereço do restaurante",example="bloco A",required=true)  // criar plugin "not blank"
 	private String complemento;
	
	@ApiModelProperty(value="Bairro do restaurante",example="Garcia",required=true)  // criar plugin "not blank"
	@NotBlank
	private String bairro;
	
	@Valid
	@NotNull
	private CidadeIdInput cidade;

}

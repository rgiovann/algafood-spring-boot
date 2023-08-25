package com.algaworks.algafood.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ApiModel(value = "Endereço", description = "Representa um endereço do restaurante")

public class EnderecoDto {

	@ApiModelProperty(value="CEP do restaurante",example="89050-000")   
	private String cep;
	
	@ApiModelProperty(value="Logradouro do restaurante",example="Av. 7 de setembro" )   
	private String logradouro;
	
	@ApiModelProperty(value="Número do restaurante",example="3500" )   
	private String numero;
	
	@ApiModelProperty(value="Complemento endereço do restaurante",example="bloco A" )   
	private String complemento;
	
	@ApiModelProperty(value="Bairro do restaurante",example="Garcia" )   
	private String bairro;
	
	private CidadeResumoDto cidade;

}

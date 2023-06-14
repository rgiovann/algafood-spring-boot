package com.algaworks.algafood.api.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class EnderecoDto {
	
	@EqualsAndHashCode.Include
	private Long id;

 	private String cep;
	
 	private String logradouro;
	
 	private String numero;
	
 	private String complemento;
	
 	private String bairro;
 
	private Long cidadeId;
	

}

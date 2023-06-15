package com.algaworks.algafood.api.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EnderecoDto {

	private Long id;

	private String cep;

	private String logradouro;

	private String numero;

	private String complemento;

	private String bairro;

	private Long cidadeId;

}

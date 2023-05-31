package com.algaworks.algafood.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Embeddable    // parte de uma outra entidade, não é uma entidade em si
public class Endereco {

	@Column(name = "endereco_cep")
	private String cep;
	
	@Column(name = "endereco_logradouro")
	private String logradouro;
	
	@Column(name = "endereco_numero")
	private String numero;
	
	@Column(name = "endereco_complemento")	
	private String complemento;
	
	@Column(name = "endereco_bairro")		
	private String bairro;
	
	
	@JoinColumn(name = "endereco_cidade_id")	// quando há associacao é @joinColumn
	@ManyToOne(fetch = FetchType.LAZY)
	private Cidade cidade;
	

}

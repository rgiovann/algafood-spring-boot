package com.algaworks.algafood.domain.model.mixin;

import java.time.OffsetTime;
import java.util.ArrayList;
import java.util.List;

import com.algaworks.algafood.domain.model.Endereco;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Produto;
import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class RestauranteMixin {
	
 	@JsonIgnore       // para evidar associacao circular
	private List<Produto> produtos = new ArrayList<Produto>();
	
	@JsonIgnore
 	private OffsetTime dataCadastro;
 	//private LocalDateTime dataCadastro;

	@JsonIgnore
 	private OffsetTime dataAtualizacao;
 	//private LocalDateTime dataAtualizacao;

	
	@JsonIgnore
 	private Endereco endereco;
	
	// abstract class, não necessita instanciar nada.
	@JsonIgnore
 	private List<FormaPagamento> formasPagamento;
	
}

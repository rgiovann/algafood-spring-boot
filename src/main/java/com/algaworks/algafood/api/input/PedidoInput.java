package com.algaworks.algafood.api.input;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoInput {
	
	@NotNull
	private RestauranteIdInput restaurante;

	@NotNull
	private EnderecoInput enderecoEntrega;
	
	@NotNull
	private FormaPagamentoIdInput formaPagamento;
	
	@Size(min=1)
	@NotNull
	private List<ItemPedidoInput> itens;

}

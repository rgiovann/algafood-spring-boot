package com.algaworks.algafood.domain.enumeration;

public enum StatusPedido {
	CRIADO("*Pedido criado*"),CONFIRMADO("*Pedido confirmado*"),ENTREGUE("*Pedido entregue*"),CANCELADO("*Pedido cancelado*");
	
	private String descricao;

	private StatusPedido(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return this.descricao;
	}

}

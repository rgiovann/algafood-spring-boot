package com.algaworks.algafood.domain.enumeration;

import java.util.Arrays;
import java.util.List;

public enum StatusPedido {
	CRIADO("*Pedido criado*"),
	CONFIRMADO("*Pedido confirmado*",CRIADO),
	ENTREGUE("*Pedido entregue*",CONFIRMADO),
	CANCELADO("*Pedido cancelado*",CRIADO);
	
	private String descricao;
	private List<StatusPedido> statusAnteriores;

	private StatusPedido(String descricao, StatusPedido...statusAnteriores ) {
		this.descricao = descricao;
		this.statusAnteriores = Arrays.asList(statusAnteriores);
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
	public boolean naoPodeAlterarPara(StatusPedido novoStatus) {
		return !novoStatus.statusAnteriores.contains(this);
	}
	
	public boolean podeAlterarPara(StatusPedido novoStatus) {
		return !naoPodeAlterarPara(novoStatus);
	}


}

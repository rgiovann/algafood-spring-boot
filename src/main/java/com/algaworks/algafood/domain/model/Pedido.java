package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import org.hibernate.annotations.CreationTimestamp;

import com.algaworks.algafood.domain.enumeration.StatusPedido;
import com.algaworks.algafood.domain.exception.NegocioException;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pedido {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String codigo;
	
	@Column(nullable =false)
	private BigDecimal subtotal;
	
	@Column(nullable =false)
	private BigDecimal taxaFrete;
	
	@Column(nullable =false)
	private BigDecimal valorTotal;
	
	@Enumerated(EnumType.STRING)
	private StatusPedido status = StatusPedido.CRIADO;
	
	@Column(nullable =false)
    @CreationTimestamp
	private OffsetDateTime dataCriacao;
	
	private OffsetDateTime dataConfirmacao;
	private OffsetDateTime dataCancelamento;
	private OffsetDateTime dataEntrega;

	@Embedded
	private Endereco enderecoEntrega;
	
	@ManyToOne 
	@JoinColumn(name ="restaurante_id", nullable=false)
	private Restaurante restaurante;
	
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	private List<ItemPedido> itens = new ArrayList<ItemPedido>();
	
 	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name ="forma_pagamento_id",nullable=false)
 	private FormaPagamento formaPagamento;
	
 	@ManyToOne 
	@JoinColumn(name ="usuario_cliente_id",nullable=false)
	private Usuario cliente;
 	
 	public void calcularValorTotal() {
 	    getItens().forEach(ItemPedido::calcularPrecoTotal);
 	    
 	    this.subtotal = getItens().stream()
 	        .map(item -> item.getPrecoTotal())
 	        .reduce(BigDecimal.ZERO, BigDecimal::add);
 	    
 	    this.valorTotal = this.subtotal.add(this.taxaFrete);
 	}
 	
 	public void confirmar() {
 		setStatus(StatusPedido.CONFIRMADO);
 		setDataConfirmacao(OffsetDateTime.now());
 	}
 	
 	public void entregar() {
 		setStatus(StatusPedido.ENTREGUE);
 		setDataEntrega(OffsetDateTime.now());
 	}
 	
 	public void cancelar() {
 		setStatus(StatusPedido.CANCELADO);
 		setDataCancelamento(OffsetDateTime.now());
 	}
 	
 	private void setStatus(StatusPedido novoStatus) {
 		if(getStatus().naoPodeAlterarPara(novoStatus)) {
			throw new NegocioException(String.format("Status do pedido %s não pode ser alterado de %s para %s",
					  this.getCodigo(),this.getStatus().getDescricao(),novoStatus.getDescricao() ));

 		}
 		
 		this.status = novoStatus;
 	}

 	// método callback do JPA
 	@PrePersist
 	private void gerarCodigo() {
 		setCodigo(UUID.randomUUID().toString());
 	}

}

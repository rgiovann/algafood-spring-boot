package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

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

import org.hibernate.annotations.CreationTimestamp;

import com.algaworks.algafood.domain.enumeration.StatusPedido;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
	
	@Column(nullable =false)
	private BigDecimal subtotal;
	
	@Column(nullable =false)
	private BigDecimal taxaFrete;
	
	@Column(nullable =false)
	private BigDecimal valorTotal;
	
	@Enumerated(EnumType.STRING)
	private StatusPedido status;
	
	@Column(nullable =false)
    @CreationTimestamp
	private OffsetDateTime dataCriacao;
	
	private OffsetDateTime dataConfirmacao;
	private OffsetDateTime dataCancelamento;
	private OffsetDateTime dataEntrega;

	@JsonIgnore
	@Embedded
	private Endereco enderecoEntrega;
	
	@JsonIgnore
	//@ManyToOne(fetch = FetchType.LAZY) 
	@ManyToOne 
	@JoinColumn(name ="restaurante_id", nullable=false)
	private Restaurante restaurante;
	
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	@JsonIgnore       // para evitar associacao circular
	private List<ItemPedido> itens = new ArrayList<ItemPedido>();
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name ="forma_pagamento_id",nullable=false)
 	private FormaPagamento formaPagamento;
	
	@JsonIgnore
	//@ManyToOne(fetch = FetchType.LAZY) 
	@ManyToOne 
	@JoinColumn(name ="usuario_cliente_id",nullable=false)
	private Usuario cliente;


}

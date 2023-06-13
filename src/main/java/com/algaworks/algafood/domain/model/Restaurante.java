package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Restaurante {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Column(nullable = false)
	private String nome;
	
	@PositiveOrZero
	@Column(name = "taxa_frete", nullable = false)
	private BigDecimal taxaFrete;

	
	@JoinColumn(name ="cozinha_id",nullable=false)
	//@ManyToOne(fetch = FetchType.LAZY)
	@ManyToOne
	private Cozinha cozinha;
	
	@OneToMany(mappedBy = "restaurante")
	private List<Produto> produtos = new ArrayList<Produto>();
	
	@CreationTimestamp
	@Column(nullable = false, columnDefinition="datetime")
	private OffsetTime dataCadastro;
	//private LocalDateTime dataCadastro;

	@UpdateTimestamp
	@Column(nullable = false, columnDefinition="datetime")
	private OffsetTime dataAtualizacao;
	//private LocalDateTime dataAtualizacao;

	@Embedded
	private Endereco endereco;
	
	@ManyToMany
	@JoinTable(name = "restaurante_forma_pagamento",
	           joinColumns = @JoinColumn(name ="restaurante_id"),
	           inverseJoinColumns = @JoinColumn(name ="forma_pagamento_id"))
	private List<FormaPagamento> formasPagamento = new ArrayList<FormaPagamento>();
	

	
}
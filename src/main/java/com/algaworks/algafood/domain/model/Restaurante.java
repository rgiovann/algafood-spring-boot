package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

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
	
//	@NotBlank // removido porque validation nas clases da API
	@Column(nullable = false)
	private String nome;
	
	private Boolean ativo = Boolean.TRUE;
	
	private Boolean aberto = Boolean.FALSE;
	
//	@PositiveOrZero  // removido porque validation nas clases da API
	@Column(name = "taxa_frete", nullable = false)
	private BigDecimal taxaFrete;

	
	@JoinColumn(name ="cozinha_id",nullable=false)
	//@ManyToOne(fetch = FetchType.LAZY)
	@ManyToOne
	private Cozinha cozinha;
	
	@OneToMany(mappedBy = "restaurante")
	private Set<Produto> produtos = new HashSet<Produto>();   // to preserve order of insertion
	
	@CreationTimestamp
	@Column(nullable = false, columnDefinition="datetime")
	private OffsetDateTime dataCadastro;
	//private OffsetDateTime dataCadastro;

	@UpdateTimestamp
	@Column(nullable = false, columnDefinition="datetime")
	private OffsetDateTime dataAtualizacao;
	//private OffsetDateTime dataAtualizacao;

	@Embedded
	private Endereco endereco;
	
	@ManyToMany
	@JoinTable(name = "restaurante_forma_pagamento",
	           joinColumns = @JoinColumn(name ="restaurante_id"),
	           inverseJoinColumns = @JoinColumn(name ="forma_pagamento_id"))
	private Set<FormaPagamento> formasPagamento = new HashSet<FormaPagamento>();
	
	public void ativar()
	{
		this.setAtivo(true);
	}
	
	public void inativar()
	{
		this.setAtivo(false);
	}
	
	public void fechar()
	{
		this.setAberto(false);
	}
	
	public void abrir()
	{
		this.setAberto(true);
	}
	
	public boolean removerFormaPagamento(FormaPagamento formaPagamento) {
		return this.getFormasPagamento().remove(formaPagamento);
	}
	
	public boolean adicionarFormaPagamento(FormaPagamento formaPagamento) {
		return this.getFormasPagamento().add(formaPagamento);
	}
	
	public boolean removerProduto(Produto produto) {
		return this.getProdutos().remove(produto);
	}
	
	public boolean adicionarProduto(Produto produto) {
		return this.getProdutos().add(produto);
	}

}
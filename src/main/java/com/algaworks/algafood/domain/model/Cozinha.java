package com.algaworks.algafood.domain.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cozinha {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//@JsonProperty("titulo")
	@NotBlank
	@Column(nullable = false)
	private String nome;
	
	@OneToMany(mappedBy = "cozinha")
	private List<Restaurante> restaurantes = new ArrayList<>();
	
	// qual o nome da propriedade que a gente usou pra fazer o mapeamento de cozinha?
//	@OneToMany(mappedBy = "cozinha")
//	@JsonIgnore       // para evidar associacao circular
//	private List<Restaurante> restaurantes = new ArrayList<Restaurante>();

//	@Override
//	public String toString() {
//		return "Cozinha [id=" + id + ", nome=" + nome + "]";
//	}

}
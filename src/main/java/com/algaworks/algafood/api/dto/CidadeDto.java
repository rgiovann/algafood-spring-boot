package com.algaworks.algafood.api.dto;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "cidades")
@ApiModel(value = "Cidade", description = "Representa uma cidade")
@Setter
@Getter
public class CidadeDto extends RepresentationModel<CidadeDto> {
	
	@ApiModelProperty(value="Id da cidade",example="1")
	private Long id;
	
	@ApiModelProperty(value="Nome da cidade",example="Blumenau")
	private String nome;
 
	EstadoDto estado;

}

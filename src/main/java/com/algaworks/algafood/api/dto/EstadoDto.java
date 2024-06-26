package com.algaworks.algafood.api.dto;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Relation(collectionRelation = "estados")
@ApiModel(value = "Estado", description = "Representa um estado.")

public class EstadoDto extends RepresentationModel<EstadoDto> {
 
	@ApiModelProperty(value="Id do estado.",example="1")
	private Long id;
	
	@ApiModelProperty(value="Nome do estado.",example="Santa Catarina")
	private String nome;
	
}

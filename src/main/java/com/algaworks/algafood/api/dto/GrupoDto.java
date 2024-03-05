package com.algaworks.algafood.api.dto;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@ApiModel(value = "Grupo de usuário", description = "Representa um grupo de usuário")

public class GrupoDto extends RepresentationModel<GrupoDto>{
 
	@ApiModelProperty(value="Id do grupo de usuário",example="1")
	private Long id;
	
	@ApiModelProperty(value="Nome do grupo de usuários",example="gerente")
	private String nome;
	
}

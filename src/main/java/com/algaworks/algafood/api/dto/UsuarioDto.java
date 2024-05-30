package com.algaworks.algafood.api.dto;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Relation(collectionRelation = "usuários")
@ApiModel(value = "Usuário", description = "Representa os dados de um usuário")

public class UsuarioDto extends RepresentationModel<UsuarioDto>{
	
	@ApiModelProperty(value="Id do usuário",example="1")
	private Long id;
	
	@ApiModelProperty(value="Nome do usuário",example="Jorge de Souza")
	private String nome;
	
	@ApiModelProperty(value="Email do usuário",example="jorge.souza@gmail.com") 
	private String email;
}

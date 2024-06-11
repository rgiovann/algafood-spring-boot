package com.algaworks.algafood.api.dto;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ApiModel(value = "Cidade", description = "Representa uma cidade do restaurante")

public class CidadeResumoDto extends RepresentationModel<CidadeResumoDto> {
	@ApiModelProperty(value="Id da cidade",example="1")
	private Long id;
	@ApiModelProperty(value="Nome da cidade",example="Blumenau")
	private String nome;
	@ApiModelProperty(value="Estado da cidade",example="Santa Catarina")
	private String estado;

}

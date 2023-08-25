package com.algaworks.algafood.api.input;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CidadeIdInput {
	
	@ApiModelProperty(value="Id da cidade",example="1")   
	@NotNull
	private Long id;

}

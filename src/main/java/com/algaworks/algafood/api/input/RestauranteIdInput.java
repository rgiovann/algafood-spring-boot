package com.algaworks.algafood.api.input;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteIdInput {
	@NotNull
	@ApiModelProperty(value="Id do restaurante",example="1" )   	
	private Long id;
}

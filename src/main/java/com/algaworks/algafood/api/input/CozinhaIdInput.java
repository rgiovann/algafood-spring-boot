package com.algaworks.algafood.api.input;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CozinhaIdInput {
	
	@NotNull
	@ApiModelProperty(value="Id do estado",example="1")
	private Long id;

}

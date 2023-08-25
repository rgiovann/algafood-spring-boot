package com.algaworks.algafood.api.input;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

/// NOT USED /// NOT USED  /// NOT USED  /// NOT USED 

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProdutoIdInput {
	
	@NotNull
	@ApiModelProperty(value="Id do produto",example="1" )   	
	private Long id;

}

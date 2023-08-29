package com.algaworks.algafood.api.input;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestauranteInput {
	@NotBlank
	@ApiModelProperty(value="Nome do restaurante", example = "Thai Gourmet", required = true)
	private String nome;
	
	@NotNull
	@ApiModelProperty(value="Valor do frete do restaurante", example = "12.45" )
	@PositiveOrZero
	private BigDecimal taxaFrete;
	
	@Valid
	@NotNull
	private CozinhaIdInput cozinha;
	
	@Valid
	@NotNull
	private EnderecoInput endereco;

}

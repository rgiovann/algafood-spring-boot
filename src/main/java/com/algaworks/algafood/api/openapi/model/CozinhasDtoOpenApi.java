package com.algaworks.algafood.api.openapi.model;

import com.algaworks.algafood.api.dto.CozinhaDto;

import io.swagger.annotations.ApiModel;

@ApiModel(value ="Cozinhas",description = "Representa uma lista paginada de cozinhas")
public class CozinhasDtoOpenApi extends PagedModelOpenApi<CozinhaDto>{
	

}

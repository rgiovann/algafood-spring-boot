package com.algaworks.algafood.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.dto.GrupoDto;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.input.GrupoNomeInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Grupos")
public interface GrupoControllerOpenApi {

	@ApiOperation("Lista os grupos")
	public CollectionModel<GrupoDto> listar();

	@ApiOperation("Busca um grupo por ID")
	@ApiResponses({
			@ApiResponse(code = 400, message = "ID da grupo inválido", response = Problem.class),
			@ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
	})
	public GrupoDto buscar(
			@ApiParam(value = "ID de um grupo", example = "1", required = true)
					Long grupoId);

	@ApiOperation("Cadastra um grupo")
	@ApiResponses({
			@ApiResponse(code = 201, message = "Grupo cadastrado"),
	})
	public GrupoDto adicionar(
			@ApiParam(name = "corpo", value = "Representação de um novo grupo", required = true)
					GrupoNomeInput grupoNomeInput);

	@ApiOperation("Atualiza um grupo por ID")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Grupo atualizado"),
			@ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
	})
	public GrupoDto atualizar(
			@ApiParam(value = "ID de um grupo", example = "1", required = true)
					Long grupoId,

			@ApiParam(name = "corpo", value = "Representação de um grupo com os novos dados",
					required = true)
			GrupoNomeInput grupoInput);

	@ApiOperation("Exclui um grupo por ID")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Grupo excluído"),
			@ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
	})
	public void remover(
			@ApiParam(value = "ID de um grupo", example = "1", required = true)
					Long grupoId);

}
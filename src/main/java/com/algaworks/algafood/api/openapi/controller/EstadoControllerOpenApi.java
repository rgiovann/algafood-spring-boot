package com.algaworks.algafood.api.openapi.controller;

import java.util.List;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.dto.EstadoDto;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.input.EstadoNomeInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Estados")
public interface EstadoControllerOpenApi {

    @ApiOperation("Lista os estados")
    CollectionModel<EstadoDto>  listar();

    @ApiOperation("Busca um estado por Id")
    @ApiResponses({
        @ApiResponse(code = 400, message = "Id do estado inválido", response = Problem.class),
        @ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
    })
    EstadoDto buscar(
            @ApiParam(value = "Id de um estado", example = "1", required = true)
            Long estadoId);

    @ApiOperation("Cadastra um estado")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Estado cadastrado"),
    })
    EstadoDto adicionar(
            @ApiParam(name = "corpo", value = "Representação de um novo estado", required = true)
            EstadoNomeInput estadoNomeInput);

    @ApiOperation("Atualiza um estado por Id")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Estado atualizado"),
        @ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
    })
    EstadoDto atualizar(
            @ApiParam(value = "Id de um estado", example = "1", required = true)
            Long estadoId,
            
            @ApiParam(name = "corpo", value = "Representação de um estado com os novos dados", required = true)
            EstadoNomeInput estadoNomeInput);

    @ApiOperation("Exclui um estado por ID")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Estado excluído"),
        @ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
    })
    void remover(
            @ApiParam(value = "Id de um estado", example = "1", required = true)
            Long estadoId);
}        
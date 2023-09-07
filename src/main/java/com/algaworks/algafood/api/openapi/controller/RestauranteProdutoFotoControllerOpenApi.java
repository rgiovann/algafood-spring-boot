package com.algaworks.algafood.api.openapi.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;

import com.algaworks.algafood.api.dto.FotoProdutoDto;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.input.FotoProdutoInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Produtos")
public interface RestauranteProdutoFotoControllerOpenApi {

    @ApiOperation("Atualiza a foto do produto de um restaurante")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Foto do produto atualizada"),
        @ApiResponse(code = 404, message = "Produto de restaurante não encontrado", response = Problem.class)
    })
    FotoProdutoDto atualizarFoto(
            @ApiParam(value = "Id do restaurante", example = "1", required = true)
            Long restauranteId,
            
            @ApiParam(value = "Id do produto", example = "1", required = true)
            Long produtoId,
            
            FotoProdutoInput fotoProdutoInput) throws IOException;

    @ApiOperation("Exclui a foto do produto de um restaurante")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Foto do produto excluída"),
        @ApiResponse(code = 400, message = "Id do restaurante ou produto inválido", response = Problem.class),
        @ApiResponse(code = 404, message = "Foto de produto não encontrada", response = Problem.class)
    })
    void excluirFoto(
            @ApiParam(value = "Id do restaurante", example = "1", required = true)
            Long restauranteId,
            
            @ApiParam(value = "Id do produto", example = "1", required = true)
            Long produtoId);

    @ApiOperation(value = "Busca a foto do produto de um restaurante")
    @ApiResponses({
        @ApiResponse(code = 400, message = "Id do restaurante ou produto inválido", response = Problem.class),
        @ApiResponse(code = 404, message = "Foto de produto não encontrada", response = Problem.class)
    })
    FotoProdutoDto consultarFoto(
            @ApiParam(value = "Id do restaurante", example = "1", required = true)
            Long restauranteId,
            
            @ApiParam(value = "Id do produto", example = "1", required = true)
            Long produtoId);

    @ApiOperation(value = "Busca a foto do produto de um restaurante", hidden = true)
    ResponseEntity<?> servirFoto(Long restauranteId, Long produtoId, String acceptHeader) 
            throws HttpMediaTypeNotAcceptableException;
}

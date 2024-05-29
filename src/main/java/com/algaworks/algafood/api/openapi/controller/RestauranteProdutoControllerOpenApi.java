package com.algaworks.algafood.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.dto.ProdutoDto;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.input.ProdutoInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Produtos")
public interface RestauranteProdutoControllerOpenApi {

    @ApiOperation("Lista os produtos de um restaurante")
    @ApiResponses({
        @ApiResponse(code = 400, message = "Id do restaurante inválido", response = Problem.class),
        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    CollectionModel<ProdutoDto> listarProdutos(
            @ApiParam(value = "Id do restaurante", example = "1", required = true)
            Long restauranteId,
            
            @ApiParam(value = "Indica se deve ou não incluir produtos inativos no resultado da listagem", 
                example = "false", defaultValue = "false")
            boolean incluirInativos);

    @ApiOperation("Busca um produto de um restaurante")
    @ApiResponses({
        @ApiResponse(code = 400, message = "Id do restaurante ou produto inválido", response = Problem.class),
        @ApiResponse(code = 404, message = "Produto de restaurante não encontrado", response = Problem.class)
    })
    ProdutoDto buscarProduto(
            @ApiParam(value = "Id do restaurante", example = "1", required = true)
            Long restauranteId,
            
            @ApiParam(value = "Id do produto", example = "1", required = true)
            Long produtoId);

    @ApiOperation("Cadastra um produto de um restaurante")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Produto cadastrado"),
        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    ProdutoDto inserirProduto(
            @ApiParam(value = "Id do restaurante", example = "1", required = true)
            Long restauranteId,
            
            @ApiParam(name = "corpo", value = "Representação de um novo produto", required = true)
            ProdutoInput produtoInput);

    @ApiOperation("Atualiza um produto de um restaurante")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Produto atualizado"),
        @ApiResponse(code = 404, message = "Produto de restaurante não encontrado", response = Problem.class)
    })
    ProdutoDto atualizarProduto(
            @ApiParam(value = "Id do restaurante", example = "1", required = true)
            Long restauranteId,
            
            @ApiParam(value = "Id do produto", example = "1", required = true)
            Long produtoId,
            
            @ApiParam(name = "corpo", value = "Representação de um produto com os novos dados", 
                required = true)
            ProdutoInput produtoInput);
}

package com.algaworks.algafood.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.dto.UsuarioDto;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.input.UsuarioComSenhaInput;
import com.algaworks.algafood.api.input.UsuarioInput;
import com.algaworks.algafood.api.input.UsuarioSoSenhaInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
@Api(tags = "Usuários")
public interface UsuarioControllerOpenApi {

    @ApiOperation("Lista os usuários")
    CollectionModel<UsuarioDto> listar();

    @ApiOperation("Busca um usuário por ID")
    @ApiResponses({
        @ApiResponse(code = 400, message = "ID do usuário inválido", response = Problem.class),
        @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
    UsuarioDto buscar(
            @ApiParam(value = "ID do usuário", example = "1", required = true)
            Long usuarioId);

    @ApiOperation("Cadastra um usuário")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Usuário cadastrado"),
    })
    UsuarioDto adicionar(
            @ApiParam(name = "corpo", value = "Representação de um novo usuário", required = true)
            UsuarioComSenhaInput usuarioInput);
    
    @ApiOperation("Atualiza um usuário por ID")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Usuário atualizado"),
        @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
    UsuarioDto atualizar(
            @ApiParam(value = "ID do usuário", example = "1", required = true)
            Long usuarioId,
            
            @ApiParam(name = "corpo", value = "Representação de um usuário com os novos dados",
                required = true)
            UsuarioInput usuarioInput);

    @ApiOperation("Atualiza a senha de um usuário")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Senha alterada com sucesso"),
        @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
    void alterarSenha(
            @ApiParam(value = "ID do usuário", example = "1", required = true)
            Long usuarioId,
            
            @ApiParam(name = "corpo", value = "Representação de uma nova senha", 
                required = true)
            UsuarioSoSenhaInput senha);
}
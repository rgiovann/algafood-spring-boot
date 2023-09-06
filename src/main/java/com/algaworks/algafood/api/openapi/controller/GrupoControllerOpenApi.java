package com.algaworks.algafood.api.openapi.controller;

import java.util.List;

import com.algaworks.algafood.api.dto.GrupoDto;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.input.GrupoNomeInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags ="Grupos")

public interface GrupoControllerOpenApi {
	
    @ApiOperation("Lista os grupos de usuários")
	List<GrupoDto> listar();
    
    @ApiResponses(  {
    @ApiResponse(responseCode= "404", description= "Grupo de usuário não encontrado",content = @Content(mediaType = "application/json",schema = @Schema(implementation = Problem.class))),

    })
    @ApiOperation("Busca uma grupo de usuário por Id")
	GrupoDto buscar(@ApiParam(value ="Id de um grupo de usuário",example ="1") Long grupoId);

 
    @ApiOperation("Cadastra uma grupo de usuário")
    @ApiResponses(  {
    @ApiResponse(responseCode= "201", description= "Grupo de usuário criado com sucesso")
    })
	GrupoDto adicionar( @ApiParam(name= "corpo",value ="Representação de um grupo de usuário")  GrupoNomeInput grupoNomeInput);

 
    @ApiOperation("Atualiza uma grupo de usuário por Id")
    @ApiResponses(  {
    @ApiResponse(responseCode= "404", description= "Grupo de usuário não encontrado",content = @Content(mediaType = "application/json",schema = @Schema(implementation = Problem.class))),
    @ApiResponse(responseCode= "200", description= "Grupo de usuário atualizado com sucesso")
    })
	GrupoDto atualizar( @ApiParam(value ="ID de um  grupo de usuário",example ="1")  Long grupoId,  GrupoNomeInput grupoNomeInput);
    
    @ApiOperation("Remove uma grupo de usuário por id")
    @ApiResponses(  {
    @ApiResponse(responseCode= "404", description= "Grupo de usuário não encontrado",content = @Content(mediaType = "application/json",schema = @Schema(implementation = Problem.class))),
    @ApiResponse(responseCode= "204", description= "Grupo de usuário removido com sucesso")
    })
	void remover(@ApiParam(value ="Id de um  grupo de usuário",example ="1") Long grupoId);	
	

}

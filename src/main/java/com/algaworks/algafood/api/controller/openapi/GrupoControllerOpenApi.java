package com.algaworks.algafood.api.controller.openapi;

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

@Api(tags ="Grupos de usuários")

public interface GrupoControllerOpenApi {
	
    @ApiOperation("Lista os grupos de usuários")
	public List<GrupoDto> listar();
    
    @ApiResponses(  {
    @ApiResponse(responseCode= "404", description= "Grupo de usuário não encontrado",content = @Content(mediaType = "application/json",schema = @Schema(implementation = Problem.class))),
    @ApiResponse(responseCode= "400", description= "Id do grupo de usuário inválido",content = @Content(mediaType = "application/json",schema = @Schema(implementation = Problem.class)))

    })
    @ApiOperation("Busca uma grupo por Id")
	public GrupoDto buscar(@ApiParam(value ="Id de um grupo de usuário",example ="1") Long grupoId);

 
    @ApiOperation("Cadastra uma grupo")
	public GrupoDto adicionar( @ApiParam(name= "corpo",value ="Representação de um grupo de usuário")  GrupoNomeInput grupoNomeInput);

 
    @ApiOperation("Atualiza uma grupo por Id")
    @ApiResponses(  {
    @ApiResponse(responseCode= "404", description= "Grupo de usuário não encontrado",content = @Content(mediaType = "application/json",schema = @Schema(implementation = Problem.class))),
    @ApiResponse(responseCode= "400", description= "Id do grupo de usuário inválido",content = @Content(mediaType = "application/json",schema = @Schema(implementation = Problem.class)))

    })
	public GrupoDto atualizar( @ApiParam(value ="ID de um  grupo de usuário",example ="1")  Long grupoId,  GrupoNomeInput grupoNomeInput);
    
    @ApiOperation("Remove uma grupo de usuário por id")
    @ApiResponses(  {
    @ApiResponse(responseCode= "404", description= "Grupo de usuário não encontrado",content = @Content(mediaType = "application/json",schema = @Schema(implementation = Problem.class))),
    @ApiResponse(responseCode= "400", description= "Id do grupo de usuário inválido",content = @Content(mediaType = "application/json",schema = @Schema(implementation = Problem.class)))

    })
	public void remover(@ApiParam(value ="Id de um  grupo de usuário",example ="1") Long grupoId);	
	

}

package com.algaworks.algafood.api.openapi.controller;

import java.util.List;

import com.algaworks.algafood.api.dto.CidadeDto;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.input.CidadeInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags ="Cidades")

public interface CidadeControllerOpenApi {
	
    @ApiOperation("Lista as cidades")
	public List<CidadeDto> listar();
    
    @ApiOperation("Busca uma cidade por id")
    @ApiResponses(  {
    @ApiResponse(responseCode= "404", description= "Cidade não encontrada",content = @Content(mediaType = "application/json",schema = @Schema(implementation = Problem.class))),
    @ApiResponse(responseCode= "400", description= "Id da cidade inválido",content = @Content(mediaType = "application/json",schema = @Schema(implementation = Problem.class)))

    })
	public CidadeDto buscar(@ApiParam(value ="ID de uma cidade",example ="1") Long cidadeId);

 
    @ApiOperation("Cadastra uma cidade")
    @ApiResponses(  {
    @ApiResponse(responseCode= "201", description= "Cidade cadastrada com sucesso"),
    })
	public CidadeDto adicionar( @ApiParam(name= "corpo",value ="Representação de uma cidade", required=true)  CidadeInput cidadeInput);

 
    @ApiOperation("Atualiza uma cidade por Id")
    @ApiResponses(  {
    @ApiResponse(responseCode= "404", description= "Cidade não encontrada",content = @Content(mediaType = "application/json",schema = @Schema(implementation = Problem.class))),
    @ApiResponse(responseCode= "200", description= "Cidade atualizada com sucesso"),


    })
	public CidadeDto atualizar( @ApiParam(value ="Id de uma cidade",example ="1" )  Long cidadeId, @ApiParam(name= "corpo",value ="Representação de uma cidade", required=true) CidadeInput cidadeInput);

    @ApiOperation("Remove uma cidade por id")
    @ApiResponses(  {
    @ApiResponse(responseCode= "404", description= "Cidade não encontrada",content = @Content(mediaType = "application/json",schema = @Schema(implementation = Problem.class))),
    @ApiResponse(responseCode= "204", description= "Cidade removida com sucesso"),
    })  
	public void remover(@ApiParam(value ="Id de uma cidade",example ="1")Long cidadeId);	
	

}

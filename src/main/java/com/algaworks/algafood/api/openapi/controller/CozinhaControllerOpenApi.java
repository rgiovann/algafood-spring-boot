package com.algaworks.algafood.api.openapi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algaworks.algafood.api.dto.CozinhaDto;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.input.CozinhaNomeInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags ="Cozinhas")

public interface CozinhaControllerOpenApi {
	
    @ApiOperation("Lista as cozinhas")
	public Page<CozinhaDto> listar(Pageable pageable);
    
    @ApiOperation("Busca uma cozinha por id")
    @ApiResponses(  {
    @ApiResponse(responseCode= "404", description= "Cozinha não encontrada",content = @Content(mediaType = "application/json",schema = @Schema(implementation = Problem.class))),
    @ApiResponse(responseCode= "400", description= "Id da cozinha inválido",content = @Content(mediaType = "application/json",schema = @Schema(implementation = Problem.class)))

    })
	public CozinhaDto buscar(@ApiParam(value ="ID de uma cozinha",example ="1") Long cozinhaId);

 
    @ApiOperation("Cadastra uma cozinha")
    @ApiResponses(  {
    @ApiResponse(responseCode= "201", description= "Cozinha cadastrada com sucesso"),
    })
	public CozinhaDto adicionar( @ApiParam(name= "corpo",value ="Representação de uma cozinha")  CozinhaNomeInput cozinhaInput);

 
    @ApiOperation("Atualiza uma cozinha por Id")
    @ApiResponses(  {
    @ApiResponse(responseCode= "404", description= "Cozinha não encontrada",content = @Content(mediaType = "application/json",schema = @Schema(implementation = Problem.class))),
    @ApiResponse(responseCode= "200", description= "Cozinha atualizada com sucesso"),


    })
	public CozinhaDto atualizar( @ApiParam(value ="Id de uma cozinha",example ="1")  Long cozinhaId, @ApiParam(name= "corpo",value ="Representação de uma cozinha")  CozinhaNomeInput cozinhaInput);

    @ApiOperation("Remove uma cozinha por id")
    @ApiResponses(  {
    @ApiResponse(responseCode= "404", description= "Cozinha não encontrada",content = @Content(mediaType = "application/json",schema = @Schema(implementation = Problem.class))),
    @ApiResponse(responseCode= "204", description= "Cozinha removida com sucesso"),
    })  
	public void remover(@ApiParam(value ="Id de uma cozinha",example ="1")Long cozinhaId);	
	

}

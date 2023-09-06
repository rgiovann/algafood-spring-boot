package com.algaworks.algafood.api.openapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

import com.algaworks.algafood.api.dto.FormaPagamentoDto;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.input.FormaPagamentoNomeInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags ="Pagamentos")

public interface FormaPagamentoControllerOpenApi {
	
    @ApiOperation("Lista as formas de Pagamento")
	ResponseEntity< List<FormaPagamentoDto> >listar(ServletWebRequest request);
    
    @ApiOperation("Busca uma forma de pagamento por id")
    @ApiResponses(  {
    @ApiResponse(responseCode= "404", description= "Forma de pagamento não encontrada",content = @Content(mediaType = "application/json",schema = @Schema(implementation = Problem.class))),
    @ApiResponse(responseCode= "400", description= "Id da forma de pagamento inválido",content = @Content(mediaType = "application/json",schema = @Schema(implementation = Problem.class)))

    })
	ResponseEntity<FormaPagamentoDto> buscar(@ApiParam(value ="ID de uma forma de pagamento",example ="1") Long formaPagamentoId,ServletWebRequest request);

 
    @ApiOperation("Cadastra uma forma de pagamento")
    @ApiResponses(  {
    @ApiResponse(responseCode= "201", description= "Forma de pagamento cadastrada com sucesso"),
    })
	FormaPagamentoDto adicionar( @ApiParam(name= "corpo",value ="Representação de uma forma de pagamento")  FormaPagamentoNomeInput formaPagamentoInput);

 
    @ApiOperation("Atualiza uma forma de pagamento por Id")
    @ApiResponses(  {
    @ApiResponse(responseCode= "404", description= "Forma de pagamento não encontrada",content = @Content(mediaType = "application/json",schema = @Schema(implementation = Problem.class))),
    @ApiResponse(responseCode= "200", description= "Forma de pagamento atualizada com sucesso"),


    })
	FormaPagamentoDto atualizar( @ApiParam(value ="Id de uma forma de pagamento",example ="1")  Long formaPagamentoId,  FormaPagamentoNomeInput formaPagamentoInput);

    @ApiOperation("Remove uma forma de pagamento por id")
    @ApiResponses(  {
    @ApiResponse(responseCode= "404", description= "Forma de pagamento não encontrada",content = @Content(mediaType = "application/json",schema = @Schema(implementation = Problem.class))),
    @ApiResponse(responseCode= "204", description= "Forma de pagamento removida com sucesso"),
    })  
	void remover(@ApiParam(value ="Id de uma forma de pagamento",example ="1")Long formaPagamentoId);	
	

}

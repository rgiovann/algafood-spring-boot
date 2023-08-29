package com.algaworks.algafood.api.openapi.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.algaworks.algafood.api.dto.RestauranteDto;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.input.RestauranteInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Api(tags ="Restaurantes")

public interface RestauranteControllerOpenApi {


 
	@GetMapping("/por-nome-taxa-frete")
	public List<RestauranteDto> restaurantesPorNomeFrete(
			@ApiParam(value = "Texto a ser pesquisado", example = "brasi",type = "query",  required = false) 
			String nome,
			@ApiParam(value = "Taxa de frete inicial da pesquisa", example = "10.45", type = "query", required = false) 
			BigDecimal taxaFreteInicial,
			@ApiParam(value = "Taxa de frete final da pesquisa", example = "12.55", type = "query", required = false) 
			 BigDecimal taxaFreteFinal); 


	@ApiOperation(value = "Busca o primeiro restaurante")
	public RestauranteDto restaurantesBuscarPrimeiro() ;

	@ApiOperation(value = "Lista resumo dos restaurantes")
	@ApiImplicitParams({
			@ApiImplicitParam(value = "Resumo dos restaurantes", allowableValues = "apenas-nome", name = "projecao", paramType = "query", dataType = "java.lang.String") })
	public List<RestauranteDto> listar(); 

	@ApiOperation(value = "Lista restaurantes", hidden = true)
	public List<RestauranteDto> listarApenasNomes();

	@ApiOperation("Busca um restaurante por Id")
	@ApiResponses({ @ApiResponse(code = 400, message = "Id do restaurante inválido", response = Problem.class),
			@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class) })
	public RestauranteDto buscar(@ApiParam(value ="Id de um restaurante",example ="1") Long restauranteId) ;

	@ApiOperation("Cadastra um restaurante")
	@ApiResponses({ @ApiResponse(code = 201, message = "Restaurante cadastrado"), })
	public RestauranteDto adicionar(@ApiParam(name= "corpo",value ="Representação de um restaurante", required = true)  RestauranteInput restauranteInput); 

	@ApiOperation("Atualiza um restaurante por Id")
	@ApiResponses({ @ApiResponse(code = 200, message = "Restaurante atualizado"),
			@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class) })
	public RestauranteDto atualizar(@ApiParam(value ="Id de um restaurante",example ="1")  Long restauranteId,
			@ApiParam(name= "corpo",value ="Representação de um restaurante", required = true)  RestauranteInput restauranteInput);

	

	@ApiOperation("Ativa um restaurante por Id")
	@ApiResponses({ @ApiResponse(code = 204, message = "Restaurante ativado com sucesso"),
			@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class) })

	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativarRestaurante(@ApiParam(value ="Id de um restaurante",example ="1")  Long restauranteId);

	@ApiOperation("Ativa múltiplos restaurantes")
	@ApiResponses({ @ApiResponse(code = 204, message = "Restaurantes ativados com sucesso") })
	public void ativarMultiplosRestaurante(@ApiParam(name= "corpo",value ="Lista de Ids de restaurantes",example="[2,10,15]")  List<Long> restauranteIds); 

	@ApiOperation("Desativa múltiplos restaurantes")
	@ApiResponses({ @ApiResponse(code = 204, message = "Restaurantes desativados com sucesso") })
	public void inativarMultiplosRestaurante(@ApiParam(name= "corpo",value ="Lista de Ids de restaurantes",example="[2,10,15]")  List<Long> restauranteIds);
	

	@ApiOperation("Inativa um restaurante por Id")
	@ApiResponses({ @ApiResponse(code = 204, message = "Restaurante inativado com sucesso"),
			@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class) })
	public void inativarRestaurante(@ApiParam(value ="Id de um restaurante",example ="1")  Long restauranteId) ;

	@ApiOperation("Abre um restaurante por Id")
	@ApiResponses({ @ApiResponse(code = 204, message = "Restaurante aberto com sucesso"),
			@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class) })
	public void abrirRestauranrte(@ApiParam(value ="Id de um restaurante",example ="1") Long restauranteId) ;

	@ApiOperation("Fecha um restaurante por Id")
	@ApiResponses({ @ApiResponse(code = 204, message = "Restaurante fechado com sucesso"),
			@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class) })
	public void fecharRestaurante(@ApiParam(value ="Id de um restaurante",example ="1")  Long restauranteId) ;

}

package com.algaworks.algafood.api.openapi.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.algaworks.algafood.api.dto.RestauranteApenasNomeDto;
import com.algaworks.algafood.api.dto.RestauranteBasicoDto;
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
	CollectionModel<RestauranteDto> restaurantesPorNomeFrete(
			@ApiParam(value = "Texto a ser pesquisado", example = "brasi",type = "query",  required = false) 
			String nome,
			@ApiParam(value = "Taxa de frete inicial da pesquisa", example = "10.45", type = "query", required = false) 
			BigDecimal taxaFreteInicial,
			@ApiParam(value = "Taxa de frete final da pesquisa", example = "12.55", type = "query", required = false) 
			 BigDecimal taxaFreteFinal); 


	@ApiOperation(value = "Busca o primeiro restaurante")
	RestauranteDto restaurantesBuscarPrimeiro() ;

	@ApiOperation(value = "Lista resumo dos restaurantes")
	@ApiImplicitParams({
			@ApiImplicitParam(value = "Resumo dos restaurantes", allowableValues = "apenas-nome", name = "projecao", paramType = "query", dataType = "java.lang.String") })
	CollectionModel<RestauranteBasicoDto> listar(); 

	@ApiOperation(value = "Lista restaurantes", hidden = true)
	CollectionModel<RestauranteApenasNomeDto> listarApenasNomes();

	@ApiOperation("Busca um restaurante por Id")
	@ApiResponses({ @ApiResponse(code = 400, message = "Id do restaurante inválido", response = Problem.class),
			@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class) })
	RestauranteDto buscar(@ApiParam(value ="Id de um restaurante",example ="1") Long restauranteId) ;

	@ApiOperation("Cadastra um restaurante")
	@ApiResponses({ @ApiResponse(code = 201, message = "Restaurante cadastrado"), })
	RestauranteDto adicionar(@ApiParam(name= "corpo",value ="Representação de um restaurante", required = true)  RestauranteInput restauranteInput); 

	@ApiOperation("Atualiza um restaurante por Id")
	@ApiResponses({ @ApiResponse(code = 200, message = "Restaurante atualizado"),
			@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class) })
	RestauranteDto atualizar(@ApiParam(value ="Id de um restaurante",example ="1")  Long restauranteId,
			@ApiParam(name= "corpo",value ="Representação de um restaurante", required = true)  RestauranteInput restauranteInput);

	

	@ApiOperation("Ativa um restaurante por Id")
	@ApiResponses({ @ApiResponse(code = 204, message = "Restaurante ativado com sucesso"),
			@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class) })

	@ResponseStatus(HttpStatus.NO_CONTENT)
	ResponseEntity<Void>  ativarRestaurante(@ApiParam(value ="Id de um restaurante",example ="1")  Long restauranteId);

	@ApiOperation("Ativa múltiplos restaurantes")
	@ApiResponses({ @ApiResponse(code = 204, message = "Restaurantes ativados com sucesso") })
	void ativarMultiplosRestaurante(@ApiParam(name= "corpo",value ="Lista de Ids de restaurantes",example="[2,10,15]")  List<Long> restauranteIds); 

	@ApiOperation("Desativa múltiplos restaurantes")
	@ApiResponses({ @ApiResponse(code = 204, message = "Restaurantes desativados com sucesso") })
	void inativarMultiplosRestaurante(@ApiParam(name= "corpo",value ="Lista de Ids de restaurantes",example="[2,10,15]")  List<Long> restauranteIds);
	

	@ApiOperation("Inativa um restaurante por Id")
	@ApiResponses({ @ApiResponse(code = 204, message = "Restaurante inativado com sucesso"),
			@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class) })
	ResponseEntity<Void>  inativarRestaurante(@ApiParam(value ="Id de um restaurante",example ="1")  Long restauranteId) ;

	@ApiOperation("Abre um restaurante por Id")
	@ApiResponses({ @ApiResponse(code = 204, message = "Restaurante aberto com sucesso"),
			@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class) })
	ResponseEntity<Void> abrirRestaurante(@ApiParam(value ="Id de um restaurante",example ="1") Long restauranteId) ;

	@ApiOperation("Fecha um restaurante por Id")
	@ApiResponses({ @ApiResponse(code = 204, message = "Restaurante fechado com sucesso"),
			@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class) })
	ResponseEntity<Void> fecharRestaurante(@ApiParam(value ="Id de um restaurante",example ="1")  Long restauranteId) ;

}

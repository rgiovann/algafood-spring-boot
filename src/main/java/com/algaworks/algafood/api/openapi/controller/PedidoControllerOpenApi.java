package com.algaworks.algafood.api.openapi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algaworks.algafood.api.dto.PedidoCompactDto;
import com.algaworks.algafood.api.dto.PedidoDto;
import com.algaworks.algafood.api.input.PedidoInput;
import com.algaworks.algafood.domain.filter.PedidoFilter;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags ="Pedidos")
public interface PedidoControllerOpenApi {

    @ApiOperation("Pesquisa os pedidos")
	@ApiImplicitParams({@ApiImplicitParam(
			value="Nomes das propriedades para filtrar na resposta, separados por vírgula",
			name="campos",paramType="query",type ="string")})
	Page<PedidoCompactDto> pesquisar(  PedidoFilter filter,  Pageable pageable) ;
	
	
	
    @ApiOperation("Busca um pedido")
	@ApiImplicitParams({@ApiImplicitParam(
			value="Nomes das propriedades para filtrar na resposta, separados por vírgula",
			name="campos",paramType="query",type ="string")})
	PedidoDto buscar(@ApiParam(value = "Código de um pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55")  String codigoPedido); 
	
    @ApiOperation("Adiciona um pedido")
	PedidoDto adicionar(@ApiParam(name = "corpo", value = "Representação de um novo pedido") PedidoInput pedidoInput);
	
}

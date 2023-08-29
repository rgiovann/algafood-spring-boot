package com.algaworks.algafood.api.openapi.model;

import com.algaworks.algafood.api.dto.PedidoCompactDto;

import io.swagger.annotations.ApiModel;

@ApiModel(value ="Pedidos",description = "Representa uma lista paginada de pedidos do restaurante")
public class PedidosDtoOpenApi extends PagedModelOpenApi<PedidoCompactDto>{
	

}

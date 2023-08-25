package com.algaworks.algafood.domain.filter;

import java.time.OffsetDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ApiModel(value = "Filtro de pedidos", description = "Define filtros para a lista de pedidos")
public class PedidoFilter {
	
	@ApiModelProperty(value="Id da cliente",example="1")
	private Long clienteId;
	@ApiModelProperty(value="Id do restaurante",example="1")
	private Long restauranteId;
	@ApiModelProperty(value="Data inicial de criação do pedido",example="2022-12-01T18:00:00Z")
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime dataCriacaoInicio;
	@ApiModelProperty(value="Data final de criação do pedido",  example="2022-12-10T18:00:00Z")
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime dataCriacaoFim;

}

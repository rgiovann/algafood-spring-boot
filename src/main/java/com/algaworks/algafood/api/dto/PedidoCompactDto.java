package com.algaworks.algafood.api.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

 
//@JsonFilter("pedidoFilter")
@Getter
@Setter
@ApiModel(value = "Pedido", description = "Representa os dados de um pedido")
public class PedidoCompactDto {
	
	@ApiModelProperty(value="Código do pedido",example="f9981ca4-5a5e-4da3-af04-933861df3e55")
	private String codigo;
	
	@ApiModelProperty(value="Subtotal do pedido (sem frete)",example="100.34")
	private BigDecimal subtotal;
	
	@ApiModelProperty(value="Valor do frete",example="10.52")	
	private BigDecimal taxaFrete;
	
	@ApiModelProperty(value="Valor total do pedido",example="110.86")	
	private BigDecimal valorTotal;
	
	@ApiModelProperty(value="Situação do pedido",example="Confirmado")		
	private String     status;  
	
	@ApiModelProperty(value="Data de criação do pedido",example="2022-12-01T18:09:02.70844Z")		
	private OffsetDateTime dataCriacao;
	
	private RestauranteCompactDto restaurante;
	
	private UsuarioDto cliente; 
	//private String nomeCliente; // modelmapper reconhece cliente.nome como nomeCliente

}

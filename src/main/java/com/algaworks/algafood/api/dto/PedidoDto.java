package com.algaworks.algafood.api.dto;

import java.time.OffsetDateTime;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

 

@Getter
@Setter
@ApiModel(value = "Pedido", description = "Representa um pedido do restaurante")

public class PedidoDto extends PedidoCompactDto{
	//private String codigo;
	//private BigDecimal subtotal;
	//private BigDecimal taxaFrete;
	//private BigDecimal valorTotal;
	//private String     status;
	//private OffsetDateTime dataCriacao;
	//private RestauranteCompactDto restaurante;
	//private UsuarioDto cliente;

	@ApiModelProperty(value="Data de confirmação do pedido",example="2022-12-01T18:09:02.00Z")		
	private OffsetDateTime dataConfirmacao;
	@ApiModelProperty(value="Data de entrega do pedido",example="2022-12-01T18:09:02.00Z")			
	private OffsetDateTime dataEntrega;
	@ApiModelProperty(value="Data de cancelamento do pedido",example="2022-12-01T18:09:02.00Z")				
	private OffsetDateTime dataCancelamento;
	private FormaPagamentoDto formaPagamento;
	private EnderecoDto enderecoEntrega;
	private List<ItemPedidoDto> itens;


}

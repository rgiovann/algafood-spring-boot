package com.algaworks.algafood.api.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

 

@Getter
@Setter
public class PedidoDto {
	private String codigo;
	private BigDecimal subtotal;
	private BigDecimal taxaFrete;
	private BigDecimal valorTotal;
	private String     status;
	private OffsetDateTime dataCriacao;
	private OffsetDateTime dataConfirmacao;
	private OffsetDateTime dataEntrega;
	private OffsetDateTime dataCancelamento;
	private RestauranteCompactDto restaurante;
	private UsuarioDto cliente;
	private FormaPagamentoDto formaPagamento;
	private EnderecoDto enderecoEntrega;
	private List<ItemPedidoDto> itens;


}

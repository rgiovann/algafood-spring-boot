package com.algaworks.algafood.domain.service;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.enumeration.StatusPedido;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Pedido;

@Service
public class FluxoPedidoService {
	
	private CadastroEmissaoPedidoService cadastroEmissaoPedido;
	
	
	
	public FluxoPedidoService(CadastroEmissaoPedidoService cadastroEmissaoPedido) {
		this.cadastroEmissaoPedido = cadastroEmissaoPedido;
	}


	@Transactional
	public void confirmarPedido(Long pedidoId)
	{
		Pedido pedido = cadastroEmissaoPedido.buscarOuFalhar(pedidoId);
		
		if(!pedido.getStatus().equals(StatusPedido.CRIADO)) {
			throw new NegocioException(String.format("Status do pedido %d não pode ser alterado de %s para %s",
					         						  pedidoId,pedido.getStatus().getDescricao(),StatusPedido.CONFIRMADO.getDescricao() ));
		}
		
		pedido.setStatus(StatusPedido.CONFIRMADO);
		pedido.setDataConfirmacao(OffsetDateTime.now());
		
	}
	
	@Transactional
	public void cancelarPedido(Long pedidoId)
	{
		Pedido pedido = cadastroEmissaoPedido.buscarOuFalhar(pedidoId);
		
		if(!pedido.getStatus().equals(StatusPedido.CRIADO)) {
			throw new NegocioException(String.format("Status do pedido %d não pode ser alterado de %s para %s",
					         						  pedidoId,pedido.getStatus().getDescricao(),StatusPedido.CANCELADO.getDescricao() ));
		}
		
		pedido.setStatus(StatusPedido.CANCELADO);
		pedido.setDataCancelamento(OffsetDateTime.now());
		
	}
	
	@Transactional
	public void entregarPedido(Long pedidoId)
	{
		Pedido pedido = cadastroEmissaoPedido.buscarOuFalhar(pedidoId);
		
		if(!pedido.getStatus().equals(StatusPedido.CONFIRMADO)) {
			throw new NegocioException(String.format("Status do pedido %d não pode ser alterado de %s para %s",
					         						  pedidoId,pedido.getStatus().getDescricao(),StatusPedido.CONFIRMADO.getDescricao() ));
		}
		
		pedido.setStatus(StatusPedido.ENTREGUE);
		pedido.setDataCancelamento(OffsetDateTime.now());
		
	}
	

}

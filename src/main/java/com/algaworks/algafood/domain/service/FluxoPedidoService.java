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
	public void confirmarPedido(String codigoPedido)
	{
		Pedido pedido = cadastroEmissaoPedido.buscarOuFalhar(codigoPedido);
		
		pedido.confirmar();

		
	}
	
	@Transactional
	public void cancelarPedido(String codigoPedido)
	{
		Pedido pedido = cadastroEmissaoPedido.buscarOuFalhar(codigoPedido);
			
		pedido.cancelar();
		
	}
	
	@Transactional
	public void entregarPedido(String codigoPedido)
	{
		Pedido pedido = cadastroEmissaoPedido.buscarOuFalhar(codigoPedido);
		
		pedido.entregar();
		
	}
	

}

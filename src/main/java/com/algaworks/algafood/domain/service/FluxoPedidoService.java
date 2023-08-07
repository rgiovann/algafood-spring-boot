package com.algaworks.algafood.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;

@Service
public class FluxoPedidoService {
	
	private final CadastroEmissaoPedidoService cadastroEmissaoPedido;
	private final PedidoRepository pedidoRepository;
	
	
	
	public FluxoPedidoService(CadastroEmissaoPedidoService cadastroEmissaoPedido,
							  PedidoRepository pedidoRepository) {
		this.cadastroEmissaoPedido = cadastroEmissaoPedido;
		this.pedidoRepository = pedidoRepository;
	
	}

	@Transactional
	public void confirmarPedido(String codigoPedido)
	{
		Pedido pedido = cadastroEmissaoPedido.buscarOuFalhar(codigoPedido);
		
		pedido.confirmar();
		
		// para trigar o eventListener precisa do save()
		pedidoRepository.save(pedido);
		
	}
	
	@Transactional
	public void cancelarPedido(String codigoPedido)
	{
		Pedido pedido = cadastroEmissaoPedido.buscarOuFalhar(codigoPedido);
			
		pedido.cancelar();
		
		pedidoRepository.save(pedido);
		
	}
	
	@Transactional
	public void entregarPedido(String codigoPedido)
	{
		Pedido pedido = cadastroEmissaoPedido.buscarOuFalhar(codigoPedido);
		
		pedido.entregar();
		
	}
	

}

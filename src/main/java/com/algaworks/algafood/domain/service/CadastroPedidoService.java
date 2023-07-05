package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.PedidoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;

@Service
public class CadastroPedidoService {

	private final PedidoRepository pedidoRepository;

	public CadastroPedidoService(PedidoRepository pedidoRepository
			                    ) {
		
		this.pedidoRepository = pedidoRepository;

	}

 
	public List<Pedido> listar() {

		return pedidoRepository.findAll();

	}
	
	public Pedido buscarOuFalhar(Long pedidoId) {
		return pedidoRepository.findById(pedidoId).orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));
	}
	


}
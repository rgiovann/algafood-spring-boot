package com.algaworks.algafood.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.service.FluxoPedidoService;

@RestController
@RequestMapping(value="/pedidos/{pedidoId}")
public class FluxoPedidoController {
	
	private FluxoPedidoService fluxoPedidoService;

	public FluxoPedidoController(FluxoPedidoService fluxoPedidoService) {
		this.fluxoPedidoService = fluxoPedidoService;
	}
	
	
	
	@PutMapping("/confirmacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void confirmarPedido(@PathVariable Long pedidoId) {
		fluxoPedidoService.confirmarPedido(pedidoId);
	}
	
	@PutMapping("/cancelamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void cancelarPedido(@PathVariable Long pedidoId) {
		fluxoPedidoService.cancelarPedido(pedidoId);
	}
	
	@PutMapping("/entrega")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void entregarPedido(@PathVariable Long pedidoId) {
		fluxoPedidoService.entregarPedido(pedidoId);
	}

}

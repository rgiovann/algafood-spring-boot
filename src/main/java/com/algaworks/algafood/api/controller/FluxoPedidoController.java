package com.algaworks.algafood.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.openapi.controller.FluxoPedidoControllerOpenApi;
import com.algaworks.algafood.domain.service.FluxoPedidoService;

@RestController
@RequestMapping(value="/pedidos/{codigoPedido}")
public class FluxoPedidoController implements FluxoPedidoControllerOpenApi {
	
	private FluxoPedidoService fluxoPedidoService;

	public FluxoPedidoController(FluxoPedidoService fluxoPedidoService) {
		this.fluxoPedidoService = fluxoPedidoService;
	}
	
	
	@Override
	@PutMapping("/confirmacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> confirmarPedido(@PathVariable String codigoPedido) {
		fluxoPedidoService.confirmarPedido(codigoPedido);
		return ResponseEntity.noContent().build();
	}
	@Override
	@PutMapping("/cancelamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> cancelarPedido(@PathVariable String codigoPedido) {
		fluxoPedidoService.cancelarPedido(codigoPedido);
		return ResponseEntity.noContent().build();

	}
	@Override
	@PutMapping("/entrega")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> entregarPedido(@PathVariable String codigoPedido) {
		fluxoPedidoService.entregarPedido(codigoPedido);
		return ResponseEntity.noContent().build();
	}

}

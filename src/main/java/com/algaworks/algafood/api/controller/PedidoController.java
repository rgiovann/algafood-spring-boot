package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.PedidoCompactDtoAssembler;
import com.algaworks.algafood.api.assembler.PedidoDtoAssembler;
import com.algaworks.algafood.api.dto.PedidoCompactDto;
import com.algaworks.algafood.api.dto.PedidoDto;
import com.algaworks.algafood.domain.service.CadastroPedidoService;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

	private final CadastroPedidoService pedidoService;
    private final PedidoDtoAssembler pedidoDtoAssembler;
    private final PedidoCompactDtoAssembler pedidoCompactDtoAssembler;


	public PedidoController(CadastroPedidoService pedidoService, 
							PedidoDtoAssembler pedidoDtoAssembler,
							PedidoCompactDtoAssembler pedidoCompactDtoAssembler
			) {
		this.pedidoService = pedidoService;
		this.pedidoDtoAssembler = pedidoDtoAssembler;
		this.pedidoCompactDtoAssembler = pedidoCompactDtoAssembler;
 	}

	@GetMapping
	public List<PedidoCompactDto> listar() {

		return pedidoCompactDtoAssembler.toCollectionDto(pedidoService.listar());
 
	}
	
	@GetMapping("/{pedidoId}")
	public PedidoDto buscar(@PathVariable Long pedidoId) {

		return  pedidoDtoAssembler.toDto(pedidoService.buscarOuFalhar(pedidoId));

	}
	
}

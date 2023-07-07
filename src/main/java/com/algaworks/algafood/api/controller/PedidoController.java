package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.PedidoCompactDtoAssembler;
import com.algaworks.algafood.api.assembler.PedidoDtoAssembler;
import com.algaworks.algafood.api.assembler.PedidoInputDisassembler;
import com.algaworks.algafood.api.dto.PedidoCompactDto;
import com.algaworks.algafood.api.dto.PedidoDto;
import com.algaworks.algafood.api.input.PedidoInput;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.CadastroEmissaoPedidoService;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

	private final CadastroEmissaoPedidoService pedidoService;
    private final PedidoDtoAssembler pedidoDtoAssembler;
    private final PedidoCompactDtoAssembler pedidoCompactDtoAssembler;
    private final PedidoInputDisassembler pedidoInputDisassembler;


	public PedidoController(CadastroEmissaoPedidoService pedidoService, 
							PedidoDtoAssembler pedidoDtoAssembler,
							PedidoCompactDtoAssembler pedidoCompactDtoAssembler,
							PedidoInputDisassembler pedidoInputDisassembler
			) {
		this.pedidoService = pedidoService;
		this.pedidoDtoAssembler = pedidoDtoAssembler;
		this.pedidoCompactDtoAssembler = pedidoCompactDtoAssembler;
		this.pedidoInputDisassembler = pedidoInputDisassembler;
 	}

	@GetMapping
	public List<PedidoCompactDto> listar() {

		return pedidoCompactDtoAssembler.toCollectionDto(pedidoService.listar());
 
	}
	
	@GetMapping("/{codigoPedido}")
	public PedidoDto buscar(@PathVariable String codigoPedido) {

		return  pedidoDtoAssembler.toDto(pedidoService.buscarOuFalhar(codigoPedido));

	}
	
//	@ResponseStatus(HttpStatus.CREATED)
//	@PostMapping
//	public PedidoDto adicionar(@RequestBody @Valid PedidoInput pedidoInput) {
//
//		try {
//
//			Pedido pedido = pedidoInputDisassembler.toEntity(pedidoInput);
//			
//			pedido.setStatus(StatusPedido.CRIADO);
// 
//			return pedidoDtoAssembler.toDto(pedidoService.emitirPedido(pedido));
//
//		} catch (CozinhaNaoEncontradaException e) {
//
//			throw new NegocioException(e.getMessage(), e);
//		} catch (CidadeNaoEncontradaException e) {
//
//			throw new NegocioException(e.getMessage(), e);
//		}
//	}
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoDto adicionar(@Valid @RequestBody PedidoInput pedidoInput) {
	    try {
	        Pedido novoPedido = pedidoInputDisassembler.toEntity(pedidoInput);

	        // TODO pegar usuário autenticado
	        novoPedido.setCliente(new Usuario());
	        novoPedido.getCliente().setId(1L);

	        novoPedido = pedidoService.emitirPedido(novoPedido);

	        return pedidoDtoAssembler.toDto(novoPedido);
	    } catch (EntidadeNaoEncontradaException e) {
	        throw new NegocioException(e.getMessage(), e);
	    }
	}
	
}

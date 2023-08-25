package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import com.algaworks.algafood.core.data.PageableTranslator;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.CadastroEmissaoPedidoService;
import com.google.common.collect.ImmutableMap;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags ="Pedidos")
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

    @ApiOperation("Pesquisa os pedidos")
	@ApiImplicitParams({@ApiImplicitParam(
			value="Nomes das propriedades para filtrar na resposta, separados por vírgula",
			name="campos",paramType="query",type ="string")})
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<PedidoCompactDto> pesquisar(  PedidoFilter filter, @PageableDefault(size=10) Pageable pageable) {
		
		pageable = this.traduzirPageable(pageable);

		Page<Pedido> pedidoPage = pedidoService.listar(filter,pageable);
		
		List<PedidoCompactDto> pedidoDtoList = pedidoCompactDtoAssembler.toCollectionDto(pedidoPage.getContent());
		
		Page<PedidoCompactDto> pedidoCompactDtoPage = new PageImpl<>(pedidoDtoList,pageable,pedidoPage.getTotalElements());
		
		
		return pedidoCompactDtoPage;
 
	}
	
	
	
//	@GetMapping
//	public MappingJacksonValue listar(@RequestParam(required=false) String campos) {
//
//		List<Pedido> pedidos = pedidoService.listar();
//		List<PedidoCompactDto> pedidosCompactDto = pedidoCompactDtoAssembler.toCollectionDto(pedidos);
//		
//		MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidosCompactDto);
//		
//		SimpleFilterProvider filterProvider = new SimpleFilterProvider();
//		
//		filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());
//
//		if (StringUtils.isNotBlank(campos)) {
//			filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept( campos.split(",")));	
//		}
//		
//		pedidosWrapper.setFilters(filterProvider);
//		
//		return pedidosWrapper;
//  
//	}
	
    @ApiOperation("Busca um pedido")
	@ApiImplicitParams({@ApiImplicitParam(
			value="Nomes das propriedades para filtrar na resposta, separados por vírgula",
			name="campos",paramType="query",type ="string")})
	@GetMapping(value ="/{codigoPedido}",produces = MediaType.APPLICATION_JSON_VALUE)
	public PedidoDto buscar(@ApiParam(value = "Código de um pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55") @PathVariable String codigoPedido) {

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
	
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Adiciona um pedido")
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoDto adicionar(@ApiParam(name = "corpo", value = "Representação de um novo pedido") @Valid @RequestBody PedidoInput pedidoInput) {
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
	
	private Pageable traduzirPageable(Pageable apiPageable) {
		
		var mapeamento = ImmutableMap.of("codigo","codigo",
				                         "restaurante.nome","restaurante.nome",
				                         "nomeCliente","cliente.nome",
				                         "valorTotal","valorTotal");
		
		return PageableTranslator.translate(apiPageable, mapeamento);
		
	}
}

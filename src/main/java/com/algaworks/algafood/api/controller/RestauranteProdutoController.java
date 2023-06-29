package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.FormaPagamentoDtoAssembler;
import com.algaworks.algafood.api.assembler.ProdutoDtoAssembler;
import com.algaworks.algafood.api.assembler.ProdutoInputDisassembler;
import com.algaworks.algafood.api.assembler.RestauranteInputDisassembler;
import com.algaworks.algafood.api.dto.ProdutoDto;
import com.algaworks.algafood.api.input.ProdutoInput;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {

	private final CadastroRestauranteService restauranteService;
  	private final ProdutoDtoAssembler produtoDtoAssembler;
  	private final ProdutoInputDisassembler produtoInputDisassembler;
  	private final CadastroProdutoService produtoService;



	public RestauranteProdutoController(CadastroRestauranteService restauranteService,
			RestauranteInputDisassembler restauranteInputDissasembler,
			ProdutoDtoAssembler produtoDtoAssembler,
			FormaPagamentoDtoAssembler formaPagtoDtoAssembler,
			ProdutoInputDisassembler produtoInputDisassembler,
			CadastroProdutoService produtoService) {

		this.restauranteService = restauranteService;
		this.produtoDtoAssembler = produtoDtoAssembler;
		this.produtoInputDisassembler = produtoInputDisassembler;
		this.produtoService = produtoService;
	}

	@GetMapping
	public List<ProdutoDto> listar(@PathVariable Long restauranteId){
		Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
		return produtoDtoAssembler.toCollectionDto(restaurante.getProdutos());		
	}
	
	@GetMapping("/{produtoId}")
 	public ProdutoDto buscarProduto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {

		Produto produto = produtoService.buscarProdutoRestaurante(restauranteId, produtoId);
		return produtoDtoAssembler.toDto(produto);
	}
	
	@PostMapping 
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoDto inserirProduto(@PathVariable Long restauranteId,@RequestBody @Valid ProdutoInput produtoInput) {

		Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
		Produto     produto = produtoInputDisassembler.toEntity(produtoInput);
		
		produto.setRestaurante(restaurante);
		
		produto = produtoService.salvar(produto);
		
		return produtoDtoAssembler.toDto(produto);
	}
	@PutMapping("/{produtoId}")
	public ProdutoDto associar(@PathVariable Long restauranteId, @PathVariable Long produtoId,@RequestBody @Valid ProdutoInput produtoInput) {
		
		restauranteService.buscarOuFalhar(restauranteId);
		Produto produto = produtoService.buscarProdutoRestaurante(restauranteId, produtoId);
		
		produtoInputDisassembler.copyToEntity(produtoInput, produto);
		
		produto = produtoService.salvar(produto);
		
		return produtoDtoAssembler.toDto(produto);
		
	}

}

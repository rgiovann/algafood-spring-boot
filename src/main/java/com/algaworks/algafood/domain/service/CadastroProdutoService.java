package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.ProdutoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.ProdutoRepository;

@Service
public class CadastroProdutoService {
	
	private final ProdutoRepository produtoRepository;
	private final CadastroRestauranteService restauranteService;

	public CadastroProdutoService(ProdutoRepository produtoRepository,
		   CadastroRestauranteService restauranteService) {
		this.produtoRepository = produtoRepository;
		this.restauranteService = restauranteService;

	}

	@Transactional
	public Produto salvar(Produto produto) {

		return produtoRepository.save(produto);
	}
	
	public Produto  buscarProdutoRestaurante(Long restauranteId, Long produtoId) {
		
		restauranteService.buscarOuFalhar(restauranteId);
		return  produtoRepository.buscaProdutoRestaurante(restauranteId,produtoId)
				.orElseThrow(() -> new ProdutoNaoEncontradoException(produtoId,restauranteId) );
		
	}

	public List<Produto> findAtivosByRestaurante(Restaurante restaurante) {
		return produtoRepository.findAtivosByRestaurante(restaurante);
	}
	
	public List<Produto> findTodosProdutosByRestaurante(Restaurante restaurante) {
		return produtoRepository.findAllByRestaurante(restaurante);
	}


}
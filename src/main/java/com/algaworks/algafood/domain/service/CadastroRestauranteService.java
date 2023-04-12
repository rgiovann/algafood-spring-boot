package com.algaworks.algafood.domain.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.RequisicaoIncorretaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();

		Cozinha cozinha = cozinhaRepository.buscar(cozinhaId);

		if (cozinha == null) {
			throw new RequisicaoIncorretaException(
					String.format("Não existe cadastro de cozinha com código %d", cozinhaId));
		}

		restaurante.setCozinha(cozinha);

		return restauranteRepository.salvar(restaurante);
	}
	
	@Transactional
	public Restaurante atualizar(Restaurante restaurante, Long restauranteId) {
		Restaurante restauranteAtual = restauranteRepository.buscar(restauranteId);
		if ( restauranteAtual == null) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe cadastro de restaurante com código %d", restauranteId));
		}
			
		BeanUtils.copyProperties(restaurante, restauranteAtual, "id");
		
		restaurante = this.salvar(restauranteAtual);
		
		return restaurante;
	}

}

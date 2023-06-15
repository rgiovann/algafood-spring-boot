package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {
	
	private static final String COZINHA_EM_USO = "Cozinha de código %d não pode ser removida, pois está em uso.";
	private final CozinhaRepository cozinhaRepository;

	public CadastroCozinhaService(CozinhaRepository cozinhaRepository) {
		this.cozinhaRepository = cozinhaRepository;

	}

	// TESTE
//	public List<Cozinha> buscarPorNome(String nomeCozinha) {
//
//		return cozinhaRepository.findTodasByNomeContaining(nomeCozinha);
//
//	}

	public List<Cozinha> listar() {

		return cozinhaRepository.findAll();

	}

	@Transactional
	public Cozinha salvar(Cozinha cozinha) {

		return cozinhaRepository.save(cozinha);
	}

	@Transactional
	public void excluir(Long cozinhaId) {
		try {

			cozinhaRepository.deleteById(cozinhaId);

		} catch (EmptyResultDataAccessException e) {
			throw new CozinhaNaoEncontradaException(cozinhaId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(COZINHA_EM_USO, cozinhaId));
		}

	}
	

	public Cozinha buscarOuFalhar(Long cozinhaId) {
		return cozinhaRepository.findById(cozinhaId).orElseThrow(() -> new CozinhaNaoEncontradaException(cozinhaId));
	}

}
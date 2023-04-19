package com.algaworks.algafood.domain.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.dto.CozinhaDto;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	public CozinhaDto buscar(Long cozinhaId) {

 		Cozinha cozinha = cozinhaRepository.findById(cozinhaId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Cozinha de código %d não encontrada.", cozinhaId)));
		
		CozinhaDto cozinhaDto = new CozinhaDto();

 		BeanUtils.copyProperties(cozinha, cozinhaDto);

		return cozinhaDto;

	}
	
	
	public List<CozinhaDto> buscarPorNome(String nomeCozinha) {
 
		List<Cozinha> cozinha = Collections.<Cozinha>emptyList();

		return cozinha.stream().map(coz -> {
			CozinhaDto cozinhaDto = new CozinhaDto();
			BeanUtils.copyProperties(coz, cozinhaDto);
			return cozinhaDto;
		}).collect(Collectors.toList());

	}
	
	

	public List<CozinhaDto> listar() {

		List<Cozinha> cozinha = cozinhaRepository.findAll();
		return cozinha.stream().map(coz -> {
			CozinhaDto cozinhaDto = new CozinhaDto();
			BeanUtils.copyProperties(coz, cozinhaDto);
			return cozinhaDto;
		}).collect(Collectors.toList());

	}

	public CozinhaDto salvar(CozinhaDto cozinhaDto) {

		Cozinha cozinha = new Cozinha();

		BeanUtils.copyProperties(cozinhaDto, cozinha);

		cozinha = cozinhaRepository.save(cozinha);

		BeanUtils.copyProperties(cozinha, cozinhaDto);

		return cozinhaDto;

	}

	public CozinhaDto atualizar(CozinhaDto cozinhaDto, Long cozinhaId) {
		
 		cozinhaRepository.findById(cozinhaId)
 		.orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Cozinha de código %d não encontrada.", cozinhaId))); 
 

		cozinhaDto.setId(cozinhaId);
		cozinhaDto = this.salvar(cozinhaDto);

		return cozinhaDto;
	}

	public void excluir(Long cozinhaId) {
		try {

			cozinhaRepository.deleteById(cozinhaId);

		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format("Cozinha de código %d não encontrada.", cozinhaId));
		}
		catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Cozinha de código %d não pode ser removida, pois está em uso.", cozinhaId));
		}

	}

}
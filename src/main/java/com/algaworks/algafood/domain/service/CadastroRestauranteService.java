package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.dto.CozinhaDto;
import com.algaworks.algafood.domain.dto.RestauranteDto;
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
	
	public RestauranteDto buscar(Long restauranteId) {
		
		Restaurante restaurante = restauranteRepository.buscar(restauranteId);
		
		if ( restaurante == null) {
			throw new EntidadeNaoEncontradaException(
					String.format("Restaurante de código %d não encontrado.", restauranteId));
		}
 	
		return copyEntityToDto(restaurante);
		
	}
	
	
	public List<RestauranteDto> listar() {
		
		List<Restaurante> restaurante = restauranteRepository.listar();
		return restaurante.stream().map(rest-> copyEntityToDto(rest) ).collect(Collectors.toList());
		
	}


	public RestauranteDto salvar(RestauranteDto restauranteDto) {
		Long cozinhaId = restauranteDto.getCozinha().getId();
		
		Restaurante restaurante = new Restaurante();

		Cozinha cozinha = cozinhaRepository.buscar(cozinhaId);
		
		if (cozinha == null) {
			throw new RequisicaoIncorretaException(
					String.format("Cozinha de código %d não encontrado.", cozinhaId));
		}

		
		BeanUtils.copyProperties(restauranteDto, restaurante);
		restaurante.setCozinha(cozinha);	
		
		restaurante = restauranteRepository.salvar(restaurante);
		
		return copyEntityToDto(restaurante);

		
	}
	
	public RestauranteDto atualizar(RestauranteDto restauranteDto, Long restauranteId) {
		Restaurante restaurante  = restauranteRepository.buscar(restauranteId);
		if ( restaurante  == null) {
			throw new EntidadeNaoEncontradaException(String.format("Restaurante de código %d não encontrado.", restauranteId));
		}
			
		restauranteDto.setId(restauranteId);
		restauranteDto = this.salvar(restauranteDto);
		
		return restauranteDto;
	}

	public void excluir(Long id) {
		try {
			
			restauranteRepository.remover(id);
		
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Restaurante de código %d não encontrado.", id));
		}		
		
	}
	
	private RestauranteDto copyEntityToDto(Restaurante restaurante) 
	{
		RestauranteDto restauranteDto= new RestauranteDto();
		CozinhaDto cozinhaDto= new CozinhaDto();
		BeanUtils.copyProperties(restaurante, restauranteDto);
		BeanUtils.copyProperties(restaurante.getCozinha(), cozinhaDto);
		restauranteDto.setCozinha(cozinhaDto);	
		return restauranteDto;
	}

}

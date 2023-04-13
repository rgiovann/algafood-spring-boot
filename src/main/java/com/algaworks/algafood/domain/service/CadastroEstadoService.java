package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.dto.EstadoDto;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {

	@Autowired
	private EstadoRepository estadoRepository;

	public EstadoDto buscar(Long estadoId) {

		Estado estado = estadoRepository.buscar(estadoId);
		EstadoDto estadoDto = new EstadoDto();

		if (estado == null) {
			throw new EntidadeNaoEncontradaException(String.format("Estado de código %d não encontrado.", estadoId));
		}

		BeanUtils.copyProperties(estado, estadoDto);

		return estadoDto;

	}

	public List<EstadoDto> listar() {

		List<Estado> estado = estadoRepository.listar();
		return estado.stream().map(est -> {
			EstadoDto estadoDto = new EstadoDto();
			BeanUtils.copyProperties(est, estadoDto);
			return estadoDto;
		}).collect(Collectors.toList());

	}

	public EstadoDto salvar(EstadoDto estadoDto) {

		Estado estado = new Estado();

		BeanUtils.copyProperties(estadoDto, estado);

		estado = estadoRepository.salvar(estado);

		BeanUtils.copyProperties(estado, estadoDto);

		return estadoDto;

	}

	public EstadoDto atualizar(EstadoDto estadoDto, Long estadoId) {
		Estado estado = estadoRepository.buscar(estadoId);
		if (estado == null) {
			throw new EntidadeNaoEncontradaException(String.format("Estado de código %d não encontrado.", estadoId));
		}

		estadoDto.setId(estadoId);
		estadoDto = this.salvar(estadoDto);

		return estadoDto;
	}

	public void excluir(Long estadoId) {
		try {

			estadoRepository.remover(estadoId);

		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format("Estado de código %d não encontrado.", estadoId));
		}

		catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Estado de código %d não pode ser removido, pois está em uso.", estadoId));
		}

	}

}
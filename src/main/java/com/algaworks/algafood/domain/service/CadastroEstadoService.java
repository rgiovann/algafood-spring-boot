package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.api.dto.EstadoDto;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {

	private static final String MSG_ESTADO_EM_USO = "Estado de código %d não pode ser removido, pois está em uso.";
	@Autowired
	private EstadoRepository estadoRepository;

	public EstadoDto buscar(Long estadoId) {
		
		Estado estado = BuscarOuFalhar( estadoId);

		EstadoDto estadoDto = new EstadoDto();

		BeanUtils.copyProperties(estado, estadoDto);

		return estadoDto;

	}

	public List<EstadoDto> listar() {

		List<Estado> estado = estadoRepository.findAll();
		return estado.stream().map(est -> {
			EstadoDto estadoDto = new EstadoDto();
			BeanUtils.copyProperties(est, estadoDto);
			return estadoDto;
		}).collect(Collectors.toList());

	}

	public EstadoDto salvar(EstadoDto estadoDto) {

		Estado estado = new Estado();

		BeanUtils.copyProperties(estadoDto, estado);

		estado = estadoRepository.save(estado);

		BeanUtils.copyProperties(estado, estadoDto);

		return estadoDto;

	}

	public EstadoDto atualizar(EstadoDto estadoDto) {
		
		BuscarOuFalhar(estadoDto.getId());

 		estadoDto = this.salvar(estadoDto);

		return estadoDto;
	}

	public void excluir(Long estadoId) {
		try {

			estadoRepository.deleteById(estadoId);

		} catch (EmptyResultDataAccessException e) {
			throw new EstadoNaoEncontradoException( estadoId);
		}

		catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_ESTADO_EM_USO, estadoId));
		}

	}
	
	public Estado BuscarOuFalhar(Long estadoId)
	{
 		return estadoRepository.findById(estadoId)
 		.orElseThrow(() -> new EstadoNaoEncontradoException(estadoId)); 
	}

}
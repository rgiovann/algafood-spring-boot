package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.dto.CidadeDto;
import com.algaworks.algafood.domain.dto.EstadoDto;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.RequisicaoIncorretaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroCidadeService {

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	public CidadeDto buscar(Long cidadeId) {
		
		Cidade cidade = cidadeRepository.buscar(cidadeId);
		
		if ( cidade == null) {
			throw new EntidadeNaoEncontradaException(
					String.format("Restaurante de código %d não encontrado.", cidadeId));
		}
 	
		return copyEntityToDto(cidade);
		
	}
	
	
	public List<CidadeDto> listar() {
		
		List<Cidade> cidade = cidadeRepository.listar();
		return cidade.stream().map(cid-> copyEntityToDto(cid) ).collect(Collectors.toList());
		
	}


	public CidadeDto salvar(CidadeDto cidadeDto) {
		
		Long estadoId = cidadeDto.getEstado().getId();
		
		Cidade cidade = new Cidade();

		Estado estado = estadoRepository.buscar(estadoId);
		
		if (estado == null) {
			throw new RequisicaoIncorretaException(
					String.format("Estado de código %d não encontrado.", estadoId));
		}

		BeanUtils.copyProperties(cidadeDto, cidade);
		cidade.setEstado(estado);	
		
		cidade = cidadeRepository.salvar(cidade);
		
		return copyEntityToDto(cidade);

		
	}
	
	public CidadeDto atualizar(CidadeDto cidadeDto, Long cidadeId) {
		Cidade cidade  = cidadeRepository.buscar(cidadeId);
		if ( cidade  == null) {
			throw new EntidadeNaoEncontradaException(String.format("Cidade de código %d não encontrado.", cidadeId));
		}
			
		cidadeDto.setId(cidadeId);
		cidadeDto = this.salvar(cidadeDto);
		
		return cidadeDto;
	}

	public void excluir(Long cidadeId) {
		try {
			
			cidadeRepository.remover(cidadeId);
		
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Cidade de código %d não encontrado.", cidadeId));
		}		
		
	}
	
	private CidadeDto copyEntityToDto(Cidade cidade) 
	{
		CidadeDto cidadeDto= new CidadeDto();
		EstadoDto estadoDto= new EstadoDto();
		BeanUtils.copyProperties(cidade, cidadeDto);
		BeanUtils.copyProperties(cidade.getEstado(), estadoDto);
		cidadeDto.setEstado(estadoDto);	
		return cidadeDto;
	}

}

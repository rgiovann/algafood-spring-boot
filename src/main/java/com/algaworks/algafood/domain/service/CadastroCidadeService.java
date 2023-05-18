package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.dto.CidadeDto;
import com.algaworks.algafood.domain.dto.EstadoDto;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroCidadeService {

	private static final String MSG_CIDADE_NAO_ENCONTRADA = "Cidade de código %d não encontrada.";

	private static final String MSG_CIDADE_EM_USO = "Cidade de código %d não pode ser removida, pois está em uso.";

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroEstadoService estadoService;
	
	@Autowired
	ModelMapper modelMapper;
	
	public CidadeDto buscar(Long cidadeId) {
		
 		Cidade cidade = buscarOuFalhar(cidadeId);  
		
 		return modelMapper.map(cidade,  CidadeDto.class);
		
	}
	
	
	public List<CidadeDto> listar() {
		
		List<Cidade> cidade = cidadeRepository.findAll();
		return cidade.stream().map(cid-> modelMapper.map(cid,  CidadeDto.class) ).collect(Collectors.toList());
		
	}


	public CidadeDto salvar(CidadeDto cidadeDto) {
		 		
		Estado estado = estadoService.BuscarOuFalhar(cidadeDto.getEstado().getId());
		
		// modelMapper não tem como saber atributos do Estado, uma vez que JSON só passa o id
		cidadeDto.setEstado(modelMapper.map(estado,  EstadoDto.class));
		
		Cidade cidade = modelMapper.map(cidadeDto,  Cidade.class);
		
		cidade = cidadeRepository.save(cidade);
		
		return modelMapper.map(cidade,  CidadeDto.class);

		
	}
	
//	public CidadeDto atualizar(CidadeDto cidadeDto) {
//		
//		cidadeDto = this.salvar(cidadeDto);
//		
//		return cidadeDto;
//	}

	public void excluir(Long cidadeId) {
		try {
			
			cidadeRepository.deleteById(cidadeId);
		
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format(MSG_CIDADE_NAO_ENCONTRADA, cidadeId));
		}
		catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_CIDADE_EM_USO, cidadeId));
		}		
		
	}
	
	public Cidade buscarOuFalhar(Long cidadeId)
	{
		return cidadeRepository.findById(cidadeId)
		 		.orElseThrow(() -> new EntidadeNaoEncontradaException(String.format(MSG_CIDADE_NAO_ENCONTRADA, cidadeId))); 
	}
	
}

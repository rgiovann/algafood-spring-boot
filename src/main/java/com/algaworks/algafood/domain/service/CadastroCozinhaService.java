package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.dto.CozinhaDto;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {

//	@Autowired
//	private CozinhaRepository cozinhaRepository;
	
	
	private static final String COZINHA_EM_USO = "Cozinha de código %d não pode ser removida, pois está em uso.";
	private final CozinhaRepository cozinhaRepository;

	  public CadastroCozinhaService(CozinhaRepository cozinhaRepository) {
	    this.cozinhaRepository = cozinhaRepository;
	  }
	

	public CozinhaDto buscar(Long cozinhaId) {

		Cozinha cozinha = buscarOuFalhar(cozinhaId);
		
		CozinhaDto cozinhaDto = new CozinhaDto();

 		BeanUtils.copyProperties(cozinha, cozinhaDto);

		return cozinhaDto;

	}
	
	
	public List<CozinhaDto> buscarPorNome(String nomeCozinha) {
 
		List<Cozinha> cozinha = cozinhaRepository.findTodasByNomeContaining(nomeCozinha);

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

	public CozinhaDto atualizar(CozinhaDto cozinhaDto) {
		
		buscarOuFalhar(cozinhaDto.getId());
 
		cozinhaDto = this.salvar(cozinhaDto);

		return cozinhaDto;
	}

	public void excluir(Long cozinhaId) {
		try {

			cozinhaRepository.deleteById(cozinhaId);

		} catch (EmptyResultDataAccessException e) {
			throw new  CozinhaNaoEncontradaException(cozinhaId) ;
		}
		catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(COZINHA_EM_USO, cozinhaId));
		}

	}
	
	public Cozinha buscarOuFalhar(Long cozinhaId) {
 		return cozinhaRepository.findById(cozinhaId)
 		.orElseThrow(() -> new CozinhaNaoEncontradaException(cozinhaId)); 
	}

}
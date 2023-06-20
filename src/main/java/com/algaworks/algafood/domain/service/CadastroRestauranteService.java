package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	private final RestauranteRepository restauranteRepository;
	private final CadastroCozinhaService cozinhaService;


	public CadastroRestauranteService(RestauranteRepository restauranteRepository, 
									  CadastroCozinhaService cozinhaService
									  ) {
		
		this.restauranteRepository = restauranteRepository;
		this.cozinhaService = cozinhaService;

	 		

	}

//	// TESTE
//	public List<RestauranteDto> buscarTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal) {
//		List<Restaurante> restaurante = restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal);
//		return restaurante.stream().map(rest -> modelMapper.map(rest, RestauranteDto.class))
//				.collect(Collectors.toList());
//	}
//
//	// TESTE
//	public List<RestauranteDto> restauranteporNome(String nome, Long cozinhaId) {
//		List<Restaurante> restaurante = restauranteRepository.consultarPorNome(nome, cozinhaId);
//		return restaurante.stream().map(rest -> modelMapper.map(rest, RestauranteDto.class))
//				.collect(Collectors.toList());
//
//	}
//
//	// TESTE
//	public List<RestauranteDto> restauranteporNomeCustomizado(String nome, BigDecimal taxaFreteInicial,
//			BigDecimal taxaFreteFinal) {
//		List<Restaurante> restaurante = restauranteRepository.findCustomizado(nome, taxaFreteInicial, taxaFreteFinal);
//		return restaurante.stream().map(rest -> modelMapper.map(rest, RestauranteDto.class))
//				.collect(Collectors.toList());
//
//	}
//
//	// TESTE
//	public List<RestauranteDto> restaurantesPorNomeComFreteGratis(String nome) {
// 
//		List<Restaurante> restaurante = restauranteRepository.findComFreteGratis(nome);
//
//		return restaurante.stream().map(rest -> modelMapper.map(rest, RestauranteDto.class))
//				.collect(Collectors.toList());
//
//	}
//
//	// TESTE
//	public Optional<RestauranteDto> restaurantesBuscarPrimeiro() {
//		Restaurante restaurante = restauranteRepository.buscarPrimeiro().get();
//		RestauranteDto restauranteDto = modelMapper.map(restaurante, RestauranteDto.class);
//		return Optional.ofNullable(restauranteDto);
//
//	}
//--------------------------------- API OFICIAL-----------------------------------------------------------------------	

//	public Restaurante buscarO(Long restauranteId) {
//	
//		return this.buscarOuFalhar(restauranteId);
//
//	}
	
	@Transactional
	public void ativar(Long restauranteId) {
		
		Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
		
		restauranteAtual.ativar();
		
	}
	
	@Transactional
	public void inativar(Long restauranteId) {
		
		Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
		
		restauranteAtual.inativar();
		
	}

	public List<Restaurante> listar() {
		
		return restauranteRepository.findAll();
	}
		
	
		@Transactional
		public Restaurante salvar(Restaurante restaurante) {
			
			Long cozinhaId = restaurante.getCozinha().getId();
			
			Cozinha cozinha = cozinhaService.buscarOuFalhar(cozinhaId);
						
			restaurante.setCozinha(cozinha);
			
			return restauranteRepository.save(restaurante);
		}
	
	
	
	public Restaurante buscarOuFalhar(Long restauranteId) {
		
		return  restauranteRepository.findById(restauranteId)
				.orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId) );
	}

}

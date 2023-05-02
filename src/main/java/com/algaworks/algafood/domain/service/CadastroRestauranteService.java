package com.algaworks.algafood.domain.service;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;

import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.algaworks.algafood.domain.dto.CozinhaDto;
import com.algaworks.algafood.domain.dto.RestauranteDto;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.RequisicaoIncorretaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CadastroRestauranteService {

//	@Autowired
//	private RestauranteRepository restauranteRepository;
//
//	@Autowired
//	private CozinhaRepository cozinhaRepository;
//	
//	private ModelMapper modelMapper;

	// modelmapper pode mapear classes dentro do Dto...
//	@Autowired
//	ModelMapper modelMapper;

	private final RestauranteRepository restauranteRepository;
	private final CozinhaRepository cozinhaRepository;
	private final ModelMapper modelMapper;

	public CadastroRestauranteService(RestauranteRepository restauranteRepository, CozinhaRepository cozinhaRepository,
			ModelMapper modelMapper) {
		this.restauranteRepository = restauranteRepository;
		this.cozinhaRepository = cozinhaRepository;
		this.modelMapper = modelMapper;
	}

	// TESTE
	public List<RestauranteDto> buscarTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal) {
		List<Restaurante> restaurante = restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal);
		return restaurante.stream().map(rest -> modelMapper.map(rest, RestauranteDto.class))
				.collect(Collectors.toList());
	}

	// TESTE
	public List<RestauranteDto> restauranteporNome(String nome, Long cozinhaId) {
		List<Restaurante> restaurante = restauranteRepository.consultarPorNome(nome, cozinhaId);
		return restaurante.stream().map(rest -> modelMapper.map(rest, RestauranteDto.class))
				.collect(Collectors.toList());

	}

	// TESTE
	public List<RestauranteDto> restauranteporNomeCustomizado(String nome, BigDecimal taxaFreteInicial,
			BigDecimal taxaFreteFinal) {
		List<Restaurante> restaurante = restauranteRepository.findCustomizado(nome, taxaFreteInicial, taxaFreteFinal);
		return restaurante.stream().map(rest -> modelMapper.map(rest, RestauranteDto.class))
				.collect(Collectors.toList());

	}

	// TESTE
	public List<RestauranteDto> restaurantesPorNomeComFreteGratis(String nome) {
		// RestauranteComFreteGratisSpec comFreteGratis = new
		// RestauranteComFreteGratisSpec();
		// RestauranteComNomeSemelhanteSpec comNomeSemelhante= new
		// RestauranteComNomeSemelhanteSpec(nome);
		List<Restaurante> restaurante = restauranteRepository.findComFreteGratis(nome);

		return restaurante.stream().map(rest -> modelMapper.map(rest, RestauranteDto.class))
				.collect(Collectors.toList());

	}

	// TESTE
	public Optional<RestauranteDto> restaurantesBuscarPrimeiro() {
		Restaurante restaurante = restauranteRepository.buscarPrimeiro().get();
		RestauranteDto restauranteDto = modelMapper.map(restaurante, RestauranteDto.class);
		return Optional.ofNullable(restauranteDto);

	}
//-------------------------------------------------------------------------------------------------------------------	

	public RestauranteDto buscar(Long restauranteId) {

		Restaurante restaurante = restauranteRepository.findById(restauranteId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(
						String.format("Restaurante de código %d não encontrado.", restauranteId)));

		return modelMapper.map(restaurante, RestauranteDto.class);

	}

	public List<RestauranteDto> listar() {

		List<Restaurante> restaurante = restauranteRepository.findAll();
		return restaurante.stream().map(rest -> modelMapper.map(rest, RestauranteDto.class))
				.collect(Collectors.toList());

	}

	public RestauranteDto salvar(RestauranteDto restauranteDto) {

		Long cozinhaId = restauranteDto.getCozinha().getId();

		Cozinha cozinha = cozinhaRepository.findById(cozinhaId).orElseThrow(() -> new EntidadeNaoEncontradaException(
				String.format("Cozinha de código %d não encontrada.", cozinhaId)));

		// modelMapper não tem como saber atributos da Cozinha, uma vez que JSON só
		// passa o id
		restauranteDto.setCozinha(modelMapper.map(cozinha, CozinhaDto.class));

		Restaurante restaurante = modelMapper.map(restauranteDto, Restaurante.class);

		restaurante = restauranteRepository.save(restaurante);

		return modelMapper.map(restaurante, RestauranteDto.class);

	}

	public RestauranteDto atualizar(RestauranteDto restauranteDto, Long restauranteId) {

		restauranteRepository.findById(restauranteId).orElseThrow(() -> new EntidadeNaoEncontradaException(
				String.format("Restaurante de código %d não encontrado.", restauranteId)));

		restauranteDto.setId(restauranteId);
		restauranteDto = this.salvar(restauranteDto);

		return restauranteDto;
	}

	public void excluir(Long restauranteId) {
		try {

			restauranteRepository.deleteById(restauranteId);

		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Restaurante de código %d não encontrado.", restauranteId));
		}

	}

	public RestauranteDto atualizarParcial(Map<String, Object> campos, Long restauranteId) {

		Restaurante restaurante = restauranteRepository.findById(restauranteId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(
						String.format("Restaurante de código %d não encontrado.", restauranteId)));

		merge(campos, restaurante);

		return atualizar(modelMapper.map(restaurante, RestauranteDto.class), restauranteId);
	}

	// **** SOLUCAO GENÉRICA ****
//	private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino) {
// 
//		try {
//			ObjectMapper objectMapper = new ObjectMapper();
// 			Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
//			dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
//				Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
//				field.setAccessible(true);
//				Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
//				ReflectionUtils.setField(field, restauranteDestino, novoValor);
//			});
//		} catch (IllegalArgumentException e) {
//			throw new RequisicaoIncorretaException(String.format("Atributo da entidade Restaurante inválido."));
//		}
//
//	}

	// **** SOLUCAO DETALHADA ****
	private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino) {
		Iterator<Map.Entry<String, Object>> itr = dadosOrigem.entrySet().iterator();
		while (itr.hasNext()) {
			Map.Entry<String, Object> entry = itr.next();
			Field field = ReflectionUtils.findField(Restaurante.class, entry.getKey());
			if (field == null) {
				throw new RequisicaoIncorretaException(
						String.format("Atributo " + entry.getKey() + " não existe para entidade Restaurante"));
			}
		}

		ObjectMapper objectMapper = new ObjectMapper();
		Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);

		dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
			Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
			field.setAccessible(true);
			Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
			ReflectionUtils.setField(field, restauranteDestino, novoValor);
		});
	}

}

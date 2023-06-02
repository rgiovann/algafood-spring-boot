package com.algaworks.algafood.domain.service;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.algaworks.algafood.domain.dto.RestauranteDto;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
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
	private final CadastroCozinhaService cozinhaService;
	//private final CozinhaRepository cozinhaRepository;
	private final ModelMapper modelMapper;
	
	

	public CadastroRestauranteService(RestauranteRepository restauranteRepository, CadastroCozinhaService cozinhaService,
			ModelMapper modelMapper) {
		this.restauranteRepository = restauranteRepository;
		this.cozinhaService = cozinhaService;
		this.modelMapper = modelMapper;	
		
	    TypeMap<Restaurante, RestauranteDto> propertyMapper = modelMapper.createTypeMap(Restaurante.class, RestauranteDto.class);
	    // add deep mapping
	    propertyMapper.addMappings(
	      mapper -> mapper.map(src -> src.getCozinha().getId(), RestauranteDto::setCozinhaId)
	    );
 		

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

		Restaurante restaurante = BuscarOuFalhar(restauranteId);

		return modelMapper.map(restaurante, RestauranteDto.class);

	}

	public List<RestauranteDto> listar() {

		List<Restaurante> restaurante = restauranteRepository.findAll();
		return restaurante.stream().map(rest -> modelMapper.map(rest, RestauranteDto.class))
				.collect(Collectors.toList());

	}


	public RestauranteDto salvar(RestauranteDto restauranteDto,Restaurante restaurante) {

 		Cozinha cozinha = cozinhaService.buscarOuFalhar(restauranteDto.getCozinhaId());
 				
 		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		modelMapper.typeMap(RestauranteDto.class, Restaurante.class)
	    .addMappings(mapper -> mapper.skip(Restaurante::setFormasPagamento))  
	    .addMappings(mapper -> mapper.skip(Restaurante::setCozinha)) 
		.addMappings(mapper -> mapper.skip(Restaurante::setProdutos))
		.addMappings(mapper -> mapper.skip(Restaurante::setEndereco)); 
//		.addMappings(mapper -> mapper.skip(Restaurante::setDataCadastro)) 
//		.addMappings(mapper -> mapper.skip(Restaurante::setDataAtualizacao)); 


		modelMapper.map(restauranteDto, restaurante);						
		restaurante.setCozinha(cozinha);
				
		restaurante = restauranteRepository.save(restaurante);	
		
		return modelMapper.map(restaurante, RestauranteDto.class);
	}


	public RestauranteDto atualizarParcial(Map<String, Object> campos, Restaurante restaurante,HttpServletRequest request) {

		RestauranteDto restauranteDto = merge(campos, restaurante, request);

		return salvar(restauranteDto,restaurante);
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
	private RestauranteDto merge(Map<String, Object> dadosOrigem, Restaurante restaurante, HttpServletRequest request) {
		
		ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
		
		RestauranteDto restauranteDtoDestino = modelMapper.map(restaurante, RestauranteDto.class);

		try {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

		RestauranteDto restauranteOrigem = objectMapper.convertValue(dadosOrigem, RestauranteDto.class);

		dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
			Field field = ReflectionUtils.findField(RestauranteDto.class, nomePropriedade);
			field.setAccessible(true);
			Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
			ReflectionUtils.setField(field, restauranteDtoDestino, novoValor);
		});
		return restauranteDtoDestino;
		} catch (IllegalArgumentException e) {
			Throwable rootCause = ExceptionUtils.getRootCause(e);
			throw new HttpMessageNotReadableException(e.getMessage(),rootCause,serverHttpRequest);
		}
	}

	
	public Restaurante BuscarOuFalhar(Long restauranteId) {
		return  restauranteRepository.findById(restauranteId)
				.orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId) );
	}

}

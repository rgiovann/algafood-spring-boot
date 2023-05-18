package com.algaworks.algafood.domain.service;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.algaworks.algafood.domain.dto.RestauranteDto;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CadastroRestauranteService {

private static final String RESTAURANTE_NAO_ENCONTRADO = "Restaurante de código %d não encontrado.";


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

//	public RestauranteDto salvar(RestauranteDto restauranteDto) {
//
// 		Cozinha cozinha = getCozinhaEntity(restauranteDto);
//
//		// modelMapper não tem como saber atributos da Cozinha, uma vez que JSON só
//		// passa o id
//         
//		Restaurante restaurante = modelMapper.map(restauranteDto, Restaurante.class);
//		
//		restaurante.setCozinha(cozinha);
//
//		restaurante = restauranteRepository.save(restaurante);
//
//		return modelMapper.map(restaurante, RestauranteDto.class);
//
//	}

	public RestauranteDto salvar(RestauranteDto restauranteDto) {

		Restaurante restaurante = BuscarOuFalhar(restauranteDto.getId());

 		Cozinha cozinha = getCozinhaEntity(restauranteDto);
 		
 		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		modelMapper.typeMap(RestauranteDto.class, Restaurante.class)
	    .addMappings(mapper -> mapper.skip(Restaurante::setFormasPagamento))  
	    .addMappings(mapper -> mapper.skip(Restaurante::setCozinha)) 
		.addMappings(mapper -> mapper.skip(Restaurante::setProdutos))
		.addMappings(mapper -> mapper.skip(Restaurante::setEndereco)); 

		modelMapper.map(restauranteDto, restaurante);						
		restaurante.setCozinha(cozinha);
				
		restaurante = restauranteRepository.save(restaurante);	
		
		return modelMapper.map(restaurante, RestauranteDto.class);
	}

//	public void excluir(Long restauranteId) {
//		try {
//
//			restauranteRepository.deleteById(restauranteId);
//
//		} catch (EmptyResultDataAccessException e) {
//			throw new EntidadeNaoEncontradaException(
//					String.format(RESTAURANTE_NAO_ENCONTRADO, restauranteId));
//		}
//
//	}

	public RestauranteDto atualizarParcial(Map<String, Object> campos, Restaurante restaurante) {

		merge(campos, restaurante);

		return salvar(modelMapper.map(restaurante, RestauranteDto.class));
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
				throw new NegocioException(
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

	private Cozinha getCozinhaEntity(RestauranteDto restauranteDto) {
		if( restauranteDto.getCozinhaId() == null) {return null;}
		Long cozinhaId = restauranteDto.getCozinhaId();

		Cozinha cozinha = cozinhaService.buscarOuFalhar(cozinhaId);

		return cozinha;

	}
	
	
	public Restaurante BuscarOuFalhar(Long restauranteId) {
		return  restauranteRepository.findById(restauranteId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(
						String.format(RESTAURANTE_NAO_ENCONTRADO, restauranteId)));
	}

}

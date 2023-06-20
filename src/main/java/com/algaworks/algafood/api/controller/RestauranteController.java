package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.dto.RestauranteDto;
import com.algaworks.algafood.api.input.CozinhaIdInput;
import com.algaworks.algafood.api.input.RestauranteInput;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteController {

	private final CadastroRestauranteService restauranteService;
	private final ModelMapper modelMapper;

	public RestauranteController(CadastroRestauranteService restauranteService, 
								 CadastroCozinhaService  cozinhaService,
			                     ModelMapper modelMapper) {
		this.restauranteService = restauranteService;
		this.modelMapper = modelMapper;
	}

//	// TESTE
//	@GetMapping("/por-taxa-frete")
//	public List<RestauranteDto> restaurantePorTaxaFrete(@RequestParam("taxaInicial") BigDecimal taxaInicial,
//			@RequestParam("taxaFinal") BigDecimal taxaFinal) {
//
//		return restauranteService.buscarTaxaFrete(taxaInicial, taxaFinal);
//
//	}
//
//	// TESTE
//	@GetMapping("/por-nome")
//	public List<RestauranteDto> restaurantePorNome(String nome, @RequestParam(value = "id") Long cozinhaId) {
//
//		return restauranteService.restauranteporNome(nome, cozinhaId);
//
//	}
//
//	// TESTE
//	@GetMapping("/por-nome-taxa-frete")
//	public List<RestauranteDto> restaurantesPorNomeFrete(String nome, BigDecimal taxaFreteInicial,
//			BigDecimal taxaFreteFinal) {
//		return restauranteService.restauranteporNomeCustomizado(nome, taxaFreteInicial, taxaFreteFinal);
//	}
//
//	// TESTE
//	@GetMapping("/com-frete-gratis")
//	public List<RestauranteDto> restaurantesPorNomeComFreteGratis(String nome) {
//		return restauranteService.restaurantesPorNomeComFreteGratis(nome);
//	}
//
//	// TESTE
//	@GetMapping("/buscar-primeiro")
//	public ResponseEntity<?> restaurantesBuscarPrimeiro() {
//		try {
//			return ResponseEntity.ok(restauranteService.restaurantesBuscarPrimeiro().get());
//		} catch (EntidadeNaoEncontradaException e) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//		}
//	}

//---------------------------------------- API OFICIAL -------------------------------------------------
//------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------

	@GetMapping
	public List<RestauranteDto> listar() {
		
		return restauranteService.listar()
				.stream()
				.map(rest -> modelMapper.map(rest, RestauranteDto.class))
				.collect(Collectors.toList());	
		}
	

	@GetMapping("/{restauranteId}")
	public RestauranteDto buscar(@PathVariable Long restauranteId) {

		return  modelMapper.map(restauranteService.buscarOuFalhar(restauranteId), RestauranteDto.class);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public RestauranteDto adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {
		
		try {
			
			Restaurante restaurante = modelMapper.map(restauranteInput, Restaurante.class);
			
			return modelMapper.map(restauranteService.salvar(restaurante), RestauranteDto.class);
			
		} catch (CozinhaNaoEncontradaException e) {
			
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@PutMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)

	public void ativar(@PathVariable Long restauranteId) {
		restauranteService.ativar(restauranteId);
	}
	
	@DeleteMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)

	public void inativar(@PathVariable Long restauranteId) {
		restauranteService.inativar(restauranteId);
	}

	@PutMapping("/{restauranteId}")
	public RestauranteDto atualizar(@PathVariable Long restauranteId,
			@RequestBody @Valid RestauranteInput restauranteInput) {
	
		Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
		
		// Para evitar org.hibernate.HibernateException: identifier of an instance of 
		// com.algaworks.algafood.domain.model.Cozinha was altered from 1 to 2		
		
		//restaurante.setCozinha(new Cozinha());
		
	    // Define o conversor
        Converter<CozinhaIdInput, Cozinha> cozinhaConverter = new Converter<CozinhaIdInput, Cozinha>() {
            @Override
            public Cozinha convert(MappingContext<CozinhaIdInput, Cozinha> context) {
            	Cozinha cozinha = new Cozinha(); 
            	cozinha.setId(context.getSource().getId());
                return cozinha;
            }
        };
        // adicina o conversor ao bean modelMapper
        modelMapper.addConverter(cozinhaConverter, CozinhaIdInput.class, Cozinha.class);
        
		modelMapper.map(restauranteInput,restaurante );
		
		try {
			return modelMapper.map(restauranteService.salvar(restaurante ), RestauranteDto.class);
		
		} catch (CozinhaNaoEncontradaException e) {
			
			throw new NegocioException(e.getMessage(), e);
		}

	}
	
}

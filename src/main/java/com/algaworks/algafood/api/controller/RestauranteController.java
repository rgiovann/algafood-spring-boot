package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

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

import com.algaworks.algafood.api.assembler.RestauranteDtoAssembler;
import com.algaworks.algafood.api.assembler.RestauranteInputDisassembler;
import com.algaworks.algafood.api.dto.RestauranteDto;
import com.algaworks.algafood.api.dto.view.RestauranteView;
import com.algaworks.algafood.api.input.RestauranteInput;
import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteController {

	private final CadastroRestauranteService restauranteService;
	private final RestauranteInputDisassembler restauranteInputDissasembler;
	private final RestauranteDtoAssembler restauranteDtoAssembler;

	public RestauranteController(CadastroRestauranteService restauranteService,
			RestauranteInputDisassembler restauranteInputDissasembler,
			RestauranteDtoAssembler restauranteDtoAssembler) {

		this.restauranteService = restauranteService;
		this.restauranteInputDissasembler = restauranteInputDissasembler;
		this.restauranteDtoAssembler = restauranteDtoAssembler;
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

	@GetMapping("/buscar-primeiro")
	public RestauranteDto restaurantesBuscarPrimeiro() {

		return restauranteDtoAssembler.toDto(restauranteService.restaurantesBuscarPrimeiro());
	}
	
	// OFICIAL // completo só no buscar por Id

	@JsonView(RestauranteView.Resumo.class)
 	@GetMapping
	public List<RestauranteDto> listar() {

		return restauranteDtoAssembler.toCollectionDto(restauranteService.listar());

	}
 
	@JsonView(RestauranteView.ApenasNome.class)
	@GetMapping(params = "projecao=nome")
	public List<RestauranteDto> listarResumido() {

		return this.listar();

	}
//	
//	@JsonView(RestauranteView.ApenasNome.class)
//	@GetMapping(params = "projecao=nome")
//	public List<RestauranteDto> listarApenasNome() {
//
//		return this.listar();
//
//	}
	
	// COMO REFERENCIA SERIALIZATION VIEW DINAMICO
// 	@GetMapping
//	public MappingJacksonValue  listar( @RequestParam(required=false) String projecao ) {
// 		
// 		List<Restaurante> restaurantes = restauranteService.listar();
//
// 		List<RestauranteDto> restaurantesDto = restauranteDtoAssembler.toCollectionDto(restaurantes);
// 		
// 		MappingJacksonValue restaurantesWraper = new MappingJacksonValue(restaurantesDto);
// 		
//		restaurantesWraper.setSerializationView(RestauranteView.Resumo.class);
//
// 		
// 		if("nome".equals(projecao)) {
// 			restaurantesWraper.setSerializationView(RestauranteView.ApenasNome.class);
// 		} else if ("completo".equals(projecao)) {
// 			restaurantesWraper.setSerializationView(null);
// 		}
// 		
// 		return restaurantesWraper;
//
//	}

	@GetMapping("/{restauranteId}")
	public RestauranteDto buscar(@PathVariable Long restauranteId) {

		return restauranteDtoAssembler.toDto(restauranteService.buscarOuFalhar(restauranteId));

	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public RestauranteDto adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {

		try {

			Restaurante restaurante = restauranteInputDissasembler.toEntity(restauranteInput);

			return restauranteDtoAssembler.toDto(restauranteService.salvar(restaurante));

		} catch (CozinhaNaoEncontradaException e) {

			throw new NegocioException(e.getMessage(), e);
		} catch (CidadeNaoEncontradaException e) {

			throw new NegocioException(e.getMessage(), e);
		}
	}

	@PutMapping("/{restauranteId}")
	public RestauranteDto atualizar(@PathVariable Long restauranteId,
			@RequestBody @Valid RestauranteInput restauranteInput) {

		Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);

		restauranteInputDissasembler.copyToEntity(restauranteInput, restaurante);

		try {
			return restauranteDtoAssembler.toDto(restauranteService.salvar(restaurante));

		} catch (CozinhaNaoEncontradaException e) {

			throw new NegocioException(e.getMessage(), e);

		} catch (CidadeNaoEncontradaException e) {

			throw new NegocioException(e.getMessage(), e);
		}

	}

	@PutMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativarRestaurante(@PathVariable Long restauranteId) {
		restauranteService.ativar(restauranteId);
	}

	@PutMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativarMultiplosRestaurante(@RequestBody List<Long> restauranteIds) {
		try {
			restauranteService.ativar(restauranteIds);
		} catch (RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@DeleteMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativarMultiplosRestaurante(@RequestBody List<Long> restauranteIds) {
		try {
			restauranteService.inativar(restauranteIds);
		} catch (RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@DeleteMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativarRestaurante(@PathVariable Long restauranteId) {
		restauranteService.inativar(restauranteId);
	}

	@PutMapping("/{restauranteId}/abertura")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void abrirRestauranrte(@PathVariable Long restauranteId) {
		restauranteService.abrir(restauranteId);
	}

	@PutMapping("/{restauranteId}/fechamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void fecharRestauranrte(@PathVariable Long restauranteId) {
		restauranteService.fechar(restauranteId);
	}

}

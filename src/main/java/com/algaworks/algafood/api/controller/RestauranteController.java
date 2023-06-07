package com.algaworks.algafood.api.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.core.validation.ValidacaoException;
import com.algaworks.algafood.domain.dto.RestauranteDto;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteController {

	private final CadastroRestauranteService restauranteService;



	public RestauranteController(CadastroRestauranteService restauranteService, SmartValidator validator) {
		this.restauranteService = restauranteService;
	}

	// TESTE
	@GetMapping("/por-taxa-frete")
	public List<RestauranteDto> restaurantePorTaxaFrete(@RequestParam("taxaInicial") BigDecimal taxaInicial,
			@RequestParam("taxaFinal") BigDecimal taxaFinal) {

		return restauranteService.buscarTaxaFrete(taxaInicial, taxaFinal);

	}

	// TESTE
	@GetMapping("/por-nome")
	public List<RestauranteDto> restaurantePorNome(String nome, @RequestParam(value = "id") Long cozinhaId) {

		return restauranteService.restauranteporNome(nome, cozinhaId);

	}

	// TESTE
	@GetMapping("/por-nome-taxa-frete")
	public List<RestauranteDto> restaurantesPorNomeFrete(String nome, BigDecimal taxaFreteInicial,
			BigDecimal taxaFreteFinal) {
		return restauranteService.restauranteporNomeCustomizado(nome, taxaFreteInicial, taxaFreteFinal);
	}

	// TESTE
	@GetMapping("/com-frete-gratis")
	public List<RestauranteDto> restaurantesPorNomeComFreteGratis(String nome) {
		return restauranteService.restaurantesPorNomeComFreteGratis(nome);
	}

	// TESTE
	@GetMapping("/buscar-primeiro")
	public ResponseEntity<?> restaurantesBuscarPrimeiro() {
		try {
			return ResponseEntity.ok(restauranteService.restaurantesBuscarPrimeiro().get());
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

//------------------------------------------------------------------------------------------------------

	@GetMapping
	public List<RestauranteDto> listar() {

		return restauranteService.listar();

	}

	@GetMapping("/{restauranteId}")
	public RestauranteDto buscar(@PathVariable Long restauranteId) {

		return restauranteService.buscar(restauranteId);

	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public RestauranteDto adicionar(@RequestBody @Valid RestauranteDto restauranteDto) {
		try {
			return restauranteService.salvar(restauranteDto,new Restaurante());
		} catch (CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@PutMapping("/{restauranteId}")
	public RestauranteDto atualizar(@PathVariable Long restauranteId, @RequestBody @Valid RestauranteDto restauranteDto) {

		Restaurante restaurante = restauranteService.BuscarOuFalhar(restauranteId);
		restauranteDto.setId(restauranteId);
		try {
			return restauranteService.salvar(restauranteDto, restaurante);
		} catch (CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}

	}

//  MODELO NAO IRÁ DELETAR O O RESTAURANTE APENAS ATIVAR/DESATIVAR
//	@DeleteMapping("/{restauranteId}")
//	@ResponseStatus(HttpStatus.NO_CONTENT)
//	public void remover(@PathVariable Long restauranteId) {
//
//		restauranteService.excluir(restauranteId);
//
//	}

	@PatchMapping("/{restauranteId}")
	public RestauranteDto atualizarParcial(@PathVariable Long restauranteId, 
			                               @RequestBody Map<String, Object> campos, 
			                               HttpServletRequest request) {

		Restaurante restaurante = restauranteService.BuscarOuFalhar(restauranteId);
				
		try {
			return restauranteService.atualizarParcial(campos, restaurante, request);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}


}

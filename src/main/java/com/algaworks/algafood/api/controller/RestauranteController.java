package com.algaworks.algafood.api.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.dto.RestauranteDto;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.RequisicaoIncorretaException;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteController {

	private final CadastroRestauranteService restauranteService;

	public RestauranteController(CadastroRestauranteService restauranteService) {
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
	public ResponseEntity<?>  restaurantesBuscarPrimeiro() {
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

	@GetMapping("/{id}")
	public ResponseEntity<?> buscar(@PathVariable Long id) {

		RestauranteDto restauranteDto;
		try {
			restauranteDto = restauranteService.buscar(id);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}

		return ResponseEntity.ok(restauranteDto);

	}

	@PostMapping
	// wild card, aceita String e Restaurante entity
	public ResponseEntity<?> adicionar(@RequestBody RestauranteDto restauranteDto) {

		try {
			restauranteDto = restauranteService.salvar(restauranteDto);
			return ResponseEntity.status(HttpStatus.CREATED).body(restauranteDto);
		} catch (RequisicaoIncorretaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

		}
	}

	@PutMapping("/{restauranteId}")
	public ResponseEntity<?> atualizar(@PathVariable Long restauranteId, @RequestBody RestauranteDto restauranteDto) {

		try {
			restauranteDto = restauranteService.atualizar(restauranteDto, restauranteId);
			return ResponseEntity.ok(restauranteDto);

		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

		} catch (RequisicaoIncorretaException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

		}

	}

	@DeleteMapping("/{restauranteId}")
	public ResponseEntity<?> remover(@PathVariable Long restauranteId) {
		try {
			restauranteService.excluir(restauranteId);

			return ResponseEntity.noContent().build();
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@PatchMapping("/{restauranteId}")
	public ResponseEntity<?> atualizarParcial(@PathVariable Long restauranteId,
			@RequestBody Map<String, Object> campos) {

		try {
			RestauranteDto restauranteDto = restauranteService.atualizarParcial(campos, restauranteId);
			return ResponseEntity.ok(restauranteDto);

		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

		} catch (RequisicaoIncorretaException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

		}

	}

}

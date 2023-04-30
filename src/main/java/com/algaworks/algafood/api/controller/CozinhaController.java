package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.dto.CozinhaDto;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

@RestController
//@RequestMapping(value = "/cozinhas",produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

//	@Autowired
//	private CadastroCozinhaService cozinhaService;
	
	private final CadastroCozinhaService cozinhaService;

	  public CozinhaController(CadastroCozinhaService cozinhaService) {
	    this.cozinhaService = cozinhaService;
	  }
	

	@GetMapping
	public List<CozinhaDto> listar() {

		return cozinhaService.listar();

	}
	
	// TESTE
	@GetMapping("/por-nome")
	public List<CozinhaDto> listarporNome(@RequestParam("nomeCozinha") String nome) {

		return cozinhaService.buscarPorNome(nome);

	}

	@GetMapping("/{cozinhaId}")
	public ResponseEntity<?> buscar(@PathVariable Long cozinhaId) {

		CozinhaDto cozinhaDto;
		try {
			cozinhaDto = cozinhaService.buscar(cozinhaId);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}

		return ResponseEntity.ok(cozinhaDto);

	}

	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody CozinhaDto cozinhaDto) {
		cozinhaDto = cozinhaService.salvar(cozinhaDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(cozinhaDto);

	}

	@PutMapping("/{cozinhaId}")
	public ResponseEntity<?> atualizar(@PathVariable Long cozinhaId, @RequestBody CozinhaDto cozinhaDto) {

		try {
			cozinhaDto = cozinhaService.atualizar(cozinhaDto, cozinhaId);
			return ResponseEntity.ok(cozinhaDto);

		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

		} 
	}

	@DeleteMapping("/{cozinhaId}")
	public ResponseEntity<?> remover(@PathVariable Long cozinhaId) {
		try {
			cozinhaService.excluir(cozinhaId);

			return ResponseEntity.noContent().build();
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}

}

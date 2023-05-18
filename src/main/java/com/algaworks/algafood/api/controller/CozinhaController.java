package com.algaworks.algafood.api.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.dto.CozinhaDto;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

@RestController
//@RequestMapping(value = "/cozinhas",produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

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
	public CozinhaDto buscar(@PathVariable Long cozinhaId) {

		return cozinhaService.buscar(cozinhaId);

	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaDto adicionar(@RequestBody CozinhaDto cozinhaDto) {
 		return cozinhaService.salvar(cozinhaDto);

	}

	@PutMapping("/{cozinhaId}")
	public CozinhaDto atualizar(@PathVariable Long cozinhaId, @RequestBody CozinhaDto cozinhaDto)
	{       cozinhaDto.setId(cozinhaId);
 			return  cozinhaService.atualizar(cozinhaDto);
 
	}

	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cozinhaId) {

			cozinhaService.excluir(cozinhaId);
	}

}

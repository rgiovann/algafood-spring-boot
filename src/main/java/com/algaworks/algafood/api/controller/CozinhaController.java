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

import com.algaworks.algafood.api.assembler.CozinhaDtoAssembler;
import com.algaworks.algafood.api.assembler.CozinhaNomeInputDisassembler;
import com.algaworks.algafood.api.dto.CozinhaDto;
import com.algaworks.algafood.api.input.CozinhaNomeInput;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

	private final CadastroCozinhaService cozinhaService;
	private final CozinhaDtoAssembler cozinhaDtoAssembler;
	private final CozinhaNomeInputDisassembler cozinhaNomeInputDisassembler;

	public CozinhaController(CadastroCozinhaService cozinhaService, 
			                 CozinhaDtoAssembler cozinhaDtoAssembler,
			                 CozinhaNomeInputDisassembler cozinhaInputDisassembler) {
		
		this.cozinhaService = cozinhaService;
		this.cozinhaDtoAssembler = cozinhaDtoAssembler;
		this.cozinhaNomeInputDisassembler = cozinhaInputDisassembler;
	}

	@GetMapping
	public List<CozinhaDto> listar() {

		return cozinhaDtoAssembler.toCollectionDto(cozinhaService.listar());

	}

	@GetMapping("/{cozinhaId}")
	public CozinhaDto buscar(@PathVariable Long cozinhaId) {

		return cozinhaDtoAssembler.toDto(cozinhaService.buscarOuFalhar(cozinhaId));

	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaDto adicionar(@RequestBody @Valid CozinhaNomeInput cozinhaNomeInput) {

		return cozinhaDtoAssembler
				.toDto(cozinhaService.salvar(cozinhaNomeInputDisassembler.toEntity(cozinhaNomeInput)));

	}

	@PutMapping("/{cozinhaId}")
	public CozinhaDto atualizar(@PathVariable Long cozinhaId, @RequestBody @Valid CozinhaNomeInput cozinhaNomeInput) {

		Cozinha cozinha = cozinhaService.buscarOuFalhar(cozinhaId);

		cozinhaNomeInputDisassembler.copyToEntity(cozinhaNomeInput, cozinha);

		return cozinhaDtoAssembler.toDto(cozinhaService.salvar(cozinha));

	}

	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cozinhaId) {

		cozinhaService.excluir(cozinhaId);
	}

}

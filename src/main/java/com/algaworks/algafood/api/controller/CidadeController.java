package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.algaworks.algafood.domain.dto.CidadeDto;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.service.CadastroCidadeService;

@RestController
//@RequestMapping(value = "/cozinhas",produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping(value = "/cidades")
public class CidadeController {

	@Autowired
	private CadastroCidadeService cidadeService;

	@GetMapping
	public List<CidadeDto> listar() {
		
		return cidadeService.listar();

	}

	@GetMapping("/{cidadeId}")
	// wild card, aceita String e Restaurante entity
	public CidadeDto buscar(@PathVariable Long cidadeId) {

		return cidadeService.buscar(cidadeId);

	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeDto adicionar(@RequestBody @Valid CidadeDto cidadeDto) {
		
		try {
			return cidadeService.salvar(cidadeDto);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(),e);
		}

	}

	@PutMapping("/{cidadeId}")
	public CidadeDto atualizar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeDto cidadeDto) {

		try {
			
			cidadeService.buscarOuFalhar(cidadeId);
			cidadeDto.setId(cidadeId);			
			return cidadeService.salvar(cidadeDto);
			
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}

	}

	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cidadeId) {

		cidadeService.excluir(cidadeId);
		return;
	}
	
}

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

import com.algaworks.algafood.api.assembler.CidadeDtoAssembler;
import com.algaworks.algafood.api.assembler.CidadeInputDisassembler;
import com.algaworks.algafood.api.dto.CidadeDto;
import com.algaworks.algafood.api.input.CidadeInput;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {

	private final CadastroCidadeService cidadeService;
	private final CidadeInputDisassembler cidadeInputDissasembler;
	private final CidadeDtoAssembler cidadeDtoAssembler;
 
 

	public CidadeController(CadastroCidadeService cidadeService, 
							CidadeInputDisassembler cidadeInputDissasembler,
							CidadeDtoAssembler cidadeDtoAssembler) {
		this.cidadeService = cidadeService;
		this.cidadeInputDissasembler = cidadeInputDissasembler;
		this.cidadeDtoAssembler = cidadeDtoAssembler;
	}

	@GetMapping
	public List<CidadeDto> listar() {

		return cidadeDtoAssembler.toCollectionDto(cidadeService.listar());

	}

	@GetMapping("/{cidadeId}")
	public CidadeDto buscar(@PathVariable Long cidadeId) {

		return cidadeDtoAssembler.toDto(cidadeService.buscarOuFalhar(cidadeId));

	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeDto adicionar(@RequestBody @Valid CidadeInput cidadeInput) {

		try {

			Cidade cidade = cidadeInputDissasembler.toEntity(cidadeInput);
			
			return cidadeDtoAssembler.toDto(cidadeService.salvar(cidade));

		} catch (EstadoNaoEncontradoException e) {

			throw new NegocioException(e.getMessage(), e);
		}

	}

	@PutMapping("/{cidadeId}")
	public CidadeDto atualizar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeInput cidadeInput) {

		Cidade cidade = cidadeService.buscarOuFalhar(cidadeId);
		
		cidadeInputDissasembler.copyToEntity(cidadeInput, cidade);
		
		try {
			
			return cidadeDtoAssembler.toDto(cidadeService.salvar(cidade));

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
;
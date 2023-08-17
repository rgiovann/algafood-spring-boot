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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags ="Cidades")
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
    @ApiOperation("Lista as cidades")
	@GetMapping
	public List<CidadeDto> listar() {

		return cidadeDtoAssembler.toCollectionDto(cidadeService.listar());

	}
    
    // *** issue ****
    // example ="1" não funciona na versão 3.0.0 (projeto descontinuado)
    // 
    @ApiOperation("Busca uma cidade por id")
	@GetMapping("/{cidadeId}")
	public CidadeDto buscar(@ApiParam(value ="ID de uma cidade",example ="1")  @PathVariable Long cidadeId) {

		return cidadeDtoAssembler.toDto(cidadeService.buscarOuFalhar(cidadeId));

	}

    // *** issue ****
    // @ApiParam(name= "corpo",value ="Representação de uma cidade") não
    // funciona na versão 3.0.0 (projeto descontinuado)
    // 
    @ApiOperation("Cadastra uma cidade")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeDto adicionar( @ApiParam(name= "corpo",value ="Representação de uma cidade")  @RequestBody @Valid CidadeInput cidadeInput) {

		try {

			Cidade cidade = cidadeInputDissasembler.toEntity(cidadeInput);
			
			return cidadeDtoAssembler.toDto(cidadeService.salvar(cidade));

		} catch (EstadoNaoEncontradoException e) {

			throw new NegocioException(e.getMessage(), e);
		}

	}

    @ApiOperation("Atualiza uma cidade por id")
	@PutMapping("/{cidadeId}")
	public CidadeDto atualizar( @ApiParam(value ="ID de uma cidade",example ="1") @PathVariable Long cidadeId, @RequestBody @Valid CidadeInput cidadeInput) {

		Cidade cidade = cidadeService.buscarOuFalhar(cidadeId);
		
		cidadeInputDissasembler.copyToEntity(cidadeInput, cidade);
		
		try {
			
			return cidadeDtoAssembler.toDto(cidadeService.salvar(cidade));

		} catch (EstadoNaoEncontradoException e) {
			
			throw new NegocioException(e.getMessage(), e);
		}

	}

    @ApiOperation("Remove uma cidade por id")
	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@ApiParam(value ="ID de uma cidade",example ="1") @PathVariable Long cidadeId) {

		cidadeService.excluir(cidadeId);
		
		return;
	}

}
;
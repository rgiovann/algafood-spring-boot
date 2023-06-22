package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
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

import com.algaworks.algafood.api.assembler.FormaPagamentoDtoAssembler;
import com.algaworks.algafood.api.assembler.FormaPagamentoInputDisassembler;
import com.algaworks.algafood.api.dto.FormaPagamentoDto;
import com.algaworks.algafood.api.input.FormaPagamentoNomeInput;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.service.FormaPagamentoService;

@RestController
@RequestMapping(value = "/formaspagamento")
public class FormaPagamentoController {

	private final FormaPagamentoService formaPagamentoService;
	private final FormaPagamentoDtoAssembler formaPagtoDtoAssembler;
	private final FormaPagamentoInputDisassembler formaPagtoInputDisassembler;

	  public FormaPagamentoController(FormaPagamentoService formaPagamentoService,
			  						  ModelMapper modelMapper,
			  						  FormaPagamentoDtoAssembler formaPagamentoDtoAssembler,
			  						  FormaPagamentoInputDisassembler formaPagtoDtoDisassembler) {
	    this.formaPagamentoService = formaPagamentoService;
	    this.formaPagtoDtoAssembler = formaPagamentoDtoAssembler;
		this.formaPagtoInputDisassembler = formaPagtoDtoDisassembler;

	  }
	
//		// TESTE
//		@GetMapping("/por-nome")
//		public List<CozinhaDto> listarporNome(@RequestParam("nomeCozinha") String nome) {
//
//			return formaPagamentoService.buscarPorNome(nome)
//					.stream()
//					.map(cozinha-> modelMapper.map(cozinha, CozinhaDto.class))
//					.collect(Collectors.toList());
//
//		}	  
		
//---------------------------------------- API OFICIAL -------------------------------------------------
//------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------

	@GetMapping
	public List<FormaPagamentoDto> listar() {

		return formaPagtoDtoAssembler.toCollectionDto(formaPagamentoService.listar());

	}
	
	@GetMapping("/{fomaPagamentoId}")
	public FormaPagamentoDto buscar(@PathVariable Long fomaPagamentoId) {

		return formaPagtoDtoAssembler.toDto(formaPagamentoService.buscarOuFalhar(fomaPagamentoId));

	}


	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FormaPagamentoDto adicionar(@RequestBody @Valid FormaPagamentoNomeInput formaPagamentoInput) {
		
		FormaPagamento formaPagamento  = formaPagamentoService
									     .salvar( formaPagtoInputDisassembler.toEntity(formaPagamentoInput)); 
 		
		return formaPagtoDtoAssembler.toDto(formaPagamento);

	}
	
	@PutMapping("/{formaPagamentoId}")
	public FormaPagamentoDto atualizar(@PathVariable Long formaPagamentoId, @RequestBody @Valid  FormaPagamentoNomeInput formaPagamentoInput)
	{        
		FormaPagamento formaPagamento  = formaPagamentoService.buscarOuFalhar(formaPagamentoId);
			
		formaPagtoInputDisassembler.copyToEntity(formaPagamentoInput, formaPagamento);
			
		return formaPagtoDtoAssembler.toDto(formaPagamentoService.salvar(formaPagamento));
 
	}

	@DeleteMapping("/{fomaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long fomaPagamentoId) {

			formaPagamentoService.excluir(fomaPagamentoId);
	}

}

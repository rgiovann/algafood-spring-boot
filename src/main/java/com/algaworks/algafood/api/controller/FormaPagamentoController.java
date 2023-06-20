package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.stream.Collectors;

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

import com.algaworks.algafood.api.dto.CozinhaDto;
import com.algaworks.algafood.api.dto.FormaPagamentoDto;
import com.algaworks.algafood.api.input.FormaPagamentoInput;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.service.FormaPagamentoService;

@RestController
//@RequestMapping(value = "/cozinhas",produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping(value = "/formaspagamento")
public class FormaPagamentoController {

	private final FormaPagamentoService formaPagamentoService;
	private ModelMapper modelMapper;

	  public FormaPagamentoController(FormaPagamentoService formaPagamentoService,ModelMapper modelMapper) {
	    this.formaPagamentoService = formaPagamentoService;
	    this.modelMapper 	= modelMapper;

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

		return formaPagamentoService.listar().stream()
		.map(formaPgto-> modelMapper.map(formaPgto, FormaPagamentoDto.class))
		.collect(Collectors.toList());

	}
	
	@GetMapping("/{fomaPagamentoId}")
	public FormaPagamentoDto buscar(@PathVariable Long fomaPagamentoId) {

		return modelMapper.map(formaPagamentoService.buscarOuFalhar(fomaPagamentoId),FormaPagamentoDto.class);

	}


	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FormaPagamentoDto adicionar(@RequestBody @Valid FormaPagamentoInput fomaPagamentoInput) {
		
		FormaPagamento formaPagamento  = formaPagamentoService.salvar(modelMapper.map(fomaPagamentoInput,FormaPagamento.class)); 
 		
		return  modelMapper.map(formaPagamento,FormaPagamentoDto.class) ;

	}
	
	@PutMapping("/{fomaPagamentoId}")
	public FormaPagamentoDto atualizar(@PathVariable Long fomaPagamentoId, @RequestBody @Valid  FormaPagamentoInput fomaPagamentoInput)
	{        
		FormaPagamento formaPagamento  = formaPagamentoService.buscarOuFalhar(fomaPagamentoId);
			
			modelMapper.map(fomaPagamentoInput,formaPagamento);
			
			return  modelMapper.map(formaPagamentoService.salvar(formaPagamento),FormaPagamentoDto.class) ;
 
	}

	@DeleteMapping("/{fomaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long fomaPagamentoId) {

			formaPagamentoService.excluir(fomaPagamentoId);
	}

}

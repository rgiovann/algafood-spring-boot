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
import com.algaworks.algafood.api.input.CozinhaNomeInput;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

@RestController
//@RequestMapping(value = "/cozinhas",produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

	private final CadastroCozinhaService cozinhaService;
	private ModelMapper modelMapper;

	  public CozinhaController(CadastroCozinhaService cozinhaService,ModelMapper modelMapper) {
	    this.cozinhaService = cozinhaService;
	    this.modelMapper 	= modelMapper;

	  }
	
//		// TESTE
//		@GetMapping("/por-nome")
//		public List<CozinhaDto> listarporNome(@RequestParam("nomeCozinha") String nome) {
//
//			return cozinhaService.buscarPorNome(nome)
//					.stream()
//					.map(cozinha-> modelMapper.map(cozinha, CozinhaDto.class))
//					.collect(Collectors.toList());
//
//		}	  
		
//---------------------------------------- API OFICIAL -------------------------------------------------
//------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------

	@GetMapping
	public List<CozinhaDto> listar() {

		return cozinhaService.listar().stream()
		.map(cozinha-> modelMapper.map(cozinha, CozinhaDto.class))
		.collect(Collectors.toList());

	}
	
	@GetMapping("/{cozinhaId}")
	public CozinhaDto buscar(@PathVariable Long cozinhaId) {

		return modelMapper.map(cozinhaService.buscarOuFalhar(cozinhaId),CozinhaDto.class);

	}


	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaDto adicionar(@RequestBody @Valid CozinhaNomeInput cozinhaInput) {
		
		Cozinha cozinha  = cozinhaService.salvar(modelMapper.map(cozinhaInput,Cozinha.class)); 
 		
		return  modelMapper.map(cozinha,CozinhaDto.class) ;

	}
	
	@PutMapping("/{cozinhaId}")
	public CozinhaDto atualizar(@PathVariable Long cozinhaId, @RequestBody @Valid  CozinhaNomeInput cozinhaInput)
	{        
			Cozinha cozinha = cozinhaService.buscarOuFalhar(cozinhaId);
			
			modelMapper.map(cozinhaInput,cozinha);
			
			return  modelMapper.map(cozinhaService.salvar(cozinha),CozinhaDto.class) ;
 
	}

	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cozinhaId) {

			cozinhaService.excluir(cozinhaId);
	}

}

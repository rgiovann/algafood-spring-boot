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

import com.algaworks.algafood.api.dto.EstadoDto;
import com.algaworks.algafood.api.input.EstadoNomeInput;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.service.CadastroEstadoService;

@RestController
//@RequestMapping(value = "/estados",produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping(value = "/estados")
public class EstadoController {

	private final CadastroEstadoService estadoService;
	private ModelMapper modelMapper;

	  public EstadoController(CadastroEstadoService estadoService,ModelMapper modelMapper) {
	    this.estadoService = estadoService;
	    this.modelMapper 	= modelMapper;

	  }
	
//---------------------------------------- API OFICIAL -------------------------------------------------
//------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------

	@GetMapping
	public List<EstadoDto> listar() {

		return estadoService.listar().stream()
		.map(estado-> modelMapper.map(estado, EstadoDto.class))
		.collect(Collectors.toList());

	}
	
	@GetMapping("/{estadoId}")
	public EstadoDto buscar(@PathVariable Long estadoId) {

		return modelMapper.map(estadoService.buscarOuFalhar(estadoId),EstadoDto.class);

	}


	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoDto adicionar(@RequestBody @Valid EstadoNomeInput estadoInput) {
		
		Estado estado  = estadoService.salvar(modelMapper.map(estadoInput,Estado.class)); 
 		
		return  modelMapper.map(estado,EstadoDto.class) ;

	}
	
	@PutMapping("/{estadoId}")
	public EstadoDto atualizar(@PathVariable Long estadoId, @RequestBody @Valid  EstadoNomeInput estadoInput)
	{        
			Estado estado = estadoService.buscarOuFalhar(estadoId);
			
			modelMapper.map(estadoInput,estado);
			
			return  modelMapper.map(estadoService.salvar(estado),EstadoDto.class) ;
 
	}

	@DeleteMapping("/{estadoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long estadoId) {

			estadoService.excluir(estadoId);
	}

}

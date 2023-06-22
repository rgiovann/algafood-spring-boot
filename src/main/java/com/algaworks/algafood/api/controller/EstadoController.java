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

import com.algaworks.algafood.api.assembler.EstadoDtoAssembler;
import com.algaworks.algafood.api.assembler.EstadoNomeInputDisassembler;
import com.algaworks.algafood.api.dto.EstadoDto;
import com.algaworks.algafood.api.input.EstadoNomeInput;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.service.CadastroEstadoService;

@RestController
@RequestMapping(value = "/estados")
public class EstadoController {

	private final CadastroEstadoService estadoService;
    private final EstadoDtoAssembler estadoDtoAssembler;
    private final EstadoNomeInputDisassembler estadoNomeInputDisassembler;
	

	public EstadoController(CadastroEstadoService estadoService, EstadoDtoAssembler estadoDtoAssembler,
			EstadoNomeInputDisassembler estadoInputDisassembler) {
		this.estadoService = estadoService;
		this.estadoDtoAssembler = estadoDtoAssembler;
		this.estadoNomeInputDisassembler = estadoInputDisassembler;
	}

	@GetMapping
	public List<EstadoDto> listar() {

		return estadoDtoAssembler.toCollectionDto(estadoService.listar());
 
	}
	
	@GetMapping("/{estadoId}")
	public EstadoDto buscar(@PathVariable Long estadoId) {

		return  estadoDtoAssembler.toDto(estadoService.buscarOuFalhar(estadoId));

	}


	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoDto adicionar(@RequestBody @Valid EstadoNomeInput estadoNomeInput) {
		
		return  estadoDtoAssembler
				.toDto( estadoService.salvar( estadoNomeInputDisassembler.toEntity(estadoNomeInput)));

	}
	
	@PutMapping("/{estadoId}")
	public EstadoDto atualizar(@PathVariable Long estadoId, @RequestBody @Valid  EstadoNomeInput estadoNomeInput)
	{        
			Estado estado = estadoService.buscarOuFalhar(estadoId);
			
			estadoNomeInputDisassembler.copyToEntity(estadoNomeInput,estado);
		 
			return  estadoDtoAssembler.toDto( estadoService.salvar(estado));
  
	}

	@DeleteMapping("/{estadoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long estadoId) {

			estadoService.excluir(estadoId);
	}

}

package com.algaworks.algafood.api.controller;

import java.util.List;

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

import com.algaworks.algafood.domain.dto.EstadoDto;
import com.algaworks.algafood.domain.service.CadastroEstadoService;

@RestController
//@RequestMapping(value = "/estados",produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping(value = "/estados")
public class EstadoController {

	@Autowired
	private CadastroEstadoService estadoService;

	@GetMapping
	public List<EstadoDto> listar() {

		return estadoService.listar();

	}

	@GetMapping("/{estadoId}")
	public  EstadoDto  buscar(@PathVariable Long estadoId) {
	
		return estadoService.buscar(estadoId);

	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoDto adicionar(@RequestBody EstadoDto estadoDto) {
 		return estadoService.salvar(estadoDto);

	}

	@PutMapping("/{estadoId}")
	public EstadoDto atualizar(@PathVariable Long estadoId, @RequestBody EstadoDto estadoDto) {

 
		estadoDto.setId(estadoId);
		return estadoService.atualizar(estadoDto);

	}

	@DeleteMapping("/{estadoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public  void remover(@PathVariable Long estadoId) {
 
		estadoService.excluir(estadoId);
		
		return;
	}

}

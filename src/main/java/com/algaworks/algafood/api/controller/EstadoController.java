package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.dto.EstadoDto;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
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
	public ResponseEntity<?> buscar(@PathVariable Long estadoId) {

		EstadoDto estadoDto;
		try {
			estadoDto = estadoService.buscar(estadoId);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}

		return ResponseEntity.ok(estadoDto);

	}

	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody EstadoDto estadoDto) {

		estadoDto = estadoService.salvar(estadoDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(estadoDto);

	}

	@PutMapping("/{estadoId}")
	public ResponseEntity<?> atualizar(@PathVariable Long estadoId, @RequestBody EstadoDto estadoDto) {

		try {
			estadoDto = estadoService.atualizar(estadoDto, estadoId);
			return ResponseEntity.ok(estadoDto);

		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

		}

	}

	@DeleteMapping("/{estadoId}")
	public ResponseEntity<?> remover(@PathVariable Long estadoId) {
		try {
			estadoService.excluir(estadoId);

			return ResponseEntity.noContent().build();
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}

}

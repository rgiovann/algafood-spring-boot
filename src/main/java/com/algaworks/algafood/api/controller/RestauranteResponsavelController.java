package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.UsuarioDtoAssembler;
import com.algaworks.algafood.api.dto.UsuarioDto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/responsaveis")
public class RestauranteResponsavelController {

	private final CadastroRestauranteService restauranteService;
  	private final UsuarioDtoAssembler usuarioDtoAssembler;


	public RestauranteResponsavelController(CadastroRestauranteService restauranteService,
			UsuarioDtoAssembler usuarioDtoAssembler) {

		this.restauranteService = restauranteService;
		this.usuarioDtoAssembler = usuarioDtoAssembler;
	}

	@GetMapping
	public List<UsuarioDto> listar(@PathVariable Long restauranteId){
		Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
		return usuarioDtoAssembler.toCollectionDto(restaurante.getResponsaveis());
		
	}
	
	@DeleteMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociarResponsavel(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		restauranteService.desassociarResponsavel(restauranteId, usuarioId);
	}
	
	@PutMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associarResponsavel(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		restauranteService.associarResponsavel(restauranteId, usuarioId);
	}

}

package com.algaworks.algafood.api.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.assembler.UsuarioDtoAssembler;
import com.algaworks.algafood.api.dto.UsuarioDto;
import com.algaworks.algafood.api.openapi.controller.RestauranteUsuarioResponsavelControllerOpenApi;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/responsaveis")
public class RestauranteUsuarioResponsavelController implements RestauranteUsuarioResponsavelControllerOpenApi {

	private final CadastroRestauranteService restauranteService;
  	private final UsuarioDtoAssembler usuarioDtoAssembler;
  	private final AlgaLinks restauranteLinks;


	public RestauranteUsuarioResponsavelController(CadastroRestauranteService restauranteService,
			UsuarioDtoAssembler usuarioDtoAssembler,AlgaLinks restauranteLinks) {

		this.restauranteService = restauranteService;
		this.usuarioDtoAssembler = usuarioDtoAssembler;
		this.restauranteLinks = restauranteLinks;
	}

	@Override
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public CollectionModel<UsuarioDto> listar(@PathVariable Long restauranteId){
		Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
		
		CollectionModel<UsuarioDto> collectionUsuarioDto = usuarioDtoAssembler.toCollectionModel(restaurante.getResponsaveis());
		
		restauranteLinks.setaRestauranteUsuarioResponsavelLink(collectionUsuarioDto, restauranteId);
		
		return collectionUsuarioDto;
		
	}
	
	@Override
	@DeleteMapping(value = "/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociarResponsavel(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		restauranteService.desassociarResponsavel(restauranteId, usuarioId);
	}
	
	@Override
	@PutMapping(value = "/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associarResponsavel(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		restauranteService.associarResponsavel(restauranteId, usuarioId);
	}

}

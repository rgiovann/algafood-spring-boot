package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.GrupoDtoAssembler;
import com.algaworks.algafood.api.dto.GrupoDto;
import com.algaworks.algafood.api.openapi.controller.UsuarioGrupoControllerOpenApi;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping(value = "/usuarios/{usuarioId}/grupos")
public class UsuarioGrupoController implements UsuarioGrupoControllerOpenApi {

	private final CadastroUsuarioService cadastroUsuarioService;

    private final GrupoDtoAssembler grupoDtoAssembler;


	public UsuarioGrupoController(	 
									GrupoDtoAssembler grupoDtoAssembler,
									CadastroUsuarioService cadastroUsuarioService) {

		this.cadastroUsuarioService = cadastroUsuarioService;
		this.grupoDtoAssembler = grupoDtoAssembler;
 	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GrupoDto> listarPermissoes(@PathVariable Long usuarioId){
		Usuario usuario = cadastroUsuarioService.buscarOuFalhar(usuarioId);
		return grupoDtoAssembler.toCollectionDto(usuario.getGrupos());
		
	}
	
	
	@DeleteMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		cadastroUsuarioService.desassociarGrupo(usuarioId, grupoId);
	}
	
	@PutMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void adicionarGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		cadastroUsuarioService.associarGrupo(usuarioId, grupoId);
	}

}

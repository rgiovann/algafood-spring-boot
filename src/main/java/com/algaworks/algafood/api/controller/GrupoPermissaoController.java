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

import com.algaworks.algafood.api.assembler.PermissaoDtoAssembler;
import com.algaworks.algafood.api.dto.PermissaoDto;
import com.algaworks.algafood.api.openapi.controller.GrupoPermissaoControllerOpenApi;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.CadastroGrupoService;

@RestController
@RequestMapping(value = "/grupos/{grupoId}/permissoes")
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {

	private final CadastroGrupoService cadastroGrupoService;
    private final PermissaoDtoAssembler permissaoDtoAssembler;


	public GrupoPermissaoController(CadastroGrupoService cadastroGrupoService,
			PermissaoDtoAssembler permissaoDtoAssembler) {

		this.cadastroGrupoService = cadastroGrupoService;
		this.permissaoDtoAssembler = permissaoDtoAssembler;
 	}
	@Override
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public CollectionModel<PermissaoDto> listarPermissoes(@PathVariable Long grupoId){
		Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);
		return permissaoDtoAssembler.toCollectionModel(grupo.getPermissoes());
		
	}
	
	@Override
	@DeleteMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		cadastroGrupoService.desassociarPermissao(grupoId, permissaoId);
	}
	@Override
	@PutMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		cadastroGrupoService.associarPermissao(grupoId, permissaoId);
	}


}

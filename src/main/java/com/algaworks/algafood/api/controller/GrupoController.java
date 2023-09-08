package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.GrupoDtoAssembler;
import com.algaworks.algafood.api.assembler.GrupoNomeInputDisassembler;
import com.algaworks.algafood.api.dto.GrupoDto;
import com.algaworks.algafood.api.input.GrupoNomeInput;
import com.algaworks.algafood.api.openapi.controller.GrupoControllerOpenApi;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.CadastroGrupoService;

@RestController
@RequestMapping(path = "/grupos")
public class GrupoController implements GrupoControllerOpenApi {

	private final CadastroGrupoService grupoService;
    private final GrupoDtoAssembler grupoDtoAssembler;
    private final GrupoNomeInputDisassembler grupoNomeInputDisassembler;


	public GrupoController(CadastroGrupoService grupoService, 
			GrupoDtoAssembler grupoDtoAssembler,
			GrupoNomeInputDisassembler grupoInputDisassembler
			) {
		this.grupoService = grupoService;
		this.grupoDtoAssembler = grupoDtoAssembler;
		this.grupoNomeInputDisassembler = grupoInputDisassembler;
 	}

	@Override
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GrupoDto> listar() {

		return grupoDtoAssembler.toCollectionDto(grupoService.listar());
 
	}
	@Override	
	@GetMapping(path="/{grupoId}",produces = MediaType.APPLICATION_JSON_VALUE)
	public GrupoDto buscar(@PathVariable Long grupoId) {

		return  grupoDtoAssembler.toDto(grupoService.buscarOuFalhar(grupoId));

	}
	@Override	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public GrupoDto adicionar(@RequestBody @Valid GrupoNomeInput grupoNomeInput) {
		
		return  grupoDtoAssembler
				.toDto( grupoService.salvar( grupoNomeInputDisassembler.toEntity(grupoNomeInput)));

	}
	@Override	
	@PutMapping(path="/{grupoId}",produces = MediaType.APPLICATION_JSON_VALUE)
	public GrupoDto atualizar(@PathVariable Long grupoId, @RequestBody @Valid  GrupoNomeInput grupoNomeInput)
	{        
			Grupo grupo = grupoService.buscarOuFalhar(grupoId);
			
			grupoNomeInputDisassembler.copyToEntity(grupoNomeInput,grupo);
		 
			return  grupoDtoAssembler.toDto( grupoService.salvar(grupo));
  
	}
	@Override
	@DeleteMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long grupoId) {

			grupoService.excluir(grupoId);
	}

}

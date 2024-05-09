package com.algaworks.algafood.api.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
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

import com.algaworks.algafood.api.assembler.CozinhaDtoAssembler;
import com.algaworks.algafood.api.assembler.CozinhaNomeInputDisassembler;
import com.algaworks.algafood.api.dto.CozinhaDto;
import com.algaworks.algafood.api.input.CozinhaNomeInput;
import com.algaworks.algafood.api.openapi.controller.CozinhaControllerOpenApi;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController implements CozinhaControllerOpenApi{

	private final CadastroCozinhaService cozinhaService;
	private final CozinhaDtoAssembler cozinhaDtoAssembler;
	private final CozinhaNomeInputDisassembler cozinhaNomeInputDisassembler;
	private final PagedResourcesAssembler <Cozinha> pagedResourceAssembler;
	
	public CozinhaController(CadastroCozinhaService cozinhaService, 
			                 CozinhaDtoAssembler cozinhaDtoAssembler,
			                 CozinhaNomeInputDisassembler cozinhaInputDisassembler,
			                 PagedResourcesAssembler <Cozinha> pagedResourceAssembler
			                 ) {
		
		this.cozinhaService = cozinhaService;
		this.cozinhaDtoAssembler = cozinhaDtoAssembler;
		this.cozinhaNomeInputDisassembler = cozinhaInputDisassembler;
		this.pagedResourceAssembler = null;
	}
	
	/*
	 * === Evitando um NullPointerException === limitação do SpringFox
     *  Ao adicionar o produces = MediaType.APPLICATION_JSON_VALUE na Annotation @RequestMapping 
     *  nos Controllers de Grupo ou Cidade, o SpringFox 3 nos apresentará um NullPointerException.
     *  Isso é um problema conhecido que ainda não foi corrigido nessa biblioteca. Acontece devido
     *  a alguns métodos das Controllers terem o retorno void, como os de DELETE, que somado ao 
     *  produces = MediaType.APPLICATION_JSON_VALUE gera um NullPointerException.
     *  Sendo assim, é necessário adicionar o produces em cada um dos métodos, 
     *  com exceção daqueles que retornam void, ao invés de adicioná-los na Controller.
	 */
	
	@Override
 	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public PagedModel<CozinhaDto> listarPaged(@PageableDefault(size=10) Pageable pageable) {
		
		Page<Cozinha> cozinhaPage = cozinhaService.listar(pageable);
		
		PagedModel<CozinhaDto> cozinhasPagedMode = pagedResourceAssembler.toModel(cozinhaPage, cozinhaDtoAssembler);
		
//		CollectionModel<CozinhaDto> cozinhaDtoCollectionModel = cozinhaDtoAssembler.toCollectionDto(cozinhaPage.getContent());
//		
//	    List<CozinhaDto> cozinhaDtoList = cozinhaDtoCollectionModel.getContent().stream().collect(Collectors.toList());
//		
//		Page<CozinhaDto> cozinhaDtoPage = new PageImpl<CozinhaDto>(cozinhaDtoList,pageable,cozinhaPage.getTotalElements());
//
//		PagedModel<CozinhaDto> cozinhaPagedModel = PagedModel.of(cozinhaDtoPage.getContent(), 
//                new PagedModel.PageMetadata(cozinhaDtoPage.getSize(),
//                                            cozinhaDtoPage.getNumber(),
//                                            cozinhaDtoPage.getTotalElements()));
		this.setLinks(null);
		return cozinhasPagedMode;
		
//		PagedModel<CozinhaDto> cozinhasPageModel = 
//				pagedResourcesAssemblerCozinha.toModel(cozinhaPage,cozinhaDtoAssembler);

	}
	
	@Override
	@GetMapping(value = "/{cozinhaId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public CozinhaDto buscar(@PathVariable Long cozinhaId) {

		return cozinhaDtoAssembler.toModel(cozinhaService.buscarOuFalhar(cozinhaId));

	}
	@Override
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaDto adicionar(@RequestBody @Valid CozinhaNomeInput cozinhaNomeInput) {

		return cozinhaDtoAssembler
				.toModel(cozinhaService.salvar(cozinhaNomeInputDisassembler.toEntity(cozinhaNomeInput)));

	}
	@Override
	@PutMapping(value = "/{cozinhaId}",produces = MediaType.APPLICATION_JSON_VALUE)
	public CozinhaDto atualizar(@PathVariable Long cozinhaId, @RequestBody @Valid CozinhaNomeInput cozinhaNomeInput) {

		Cozinha cozinha = cozinhaService.buscarOuFalhar(cozinhaId);

		cozinhaNomeInputDisassembler.copyToEntity(cozinhaNomeInput, cozinha);

		return cozinhaDtoAssembler.toModel(cozinhaService.salvar(cozinha));

	}
	@Override
	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cozinhaId) {

		cozinhaService.excluir(cozinhaId);
	}
	


}

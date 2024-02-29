package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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
import com.algaworks.algafood.api.ResourceUriHelper;
import com.algaworks.algafood.api.assembler.CidadeDtoAssembler;
import com.algaworks.algafood.api.assembler.CidadeInputDisassembler;
import com.algaworks.algafood.api.dto.CidadeDto;
import com.algaworks.algafood.api.input.CidadeInput;
import com.algaworks.algafood.api.openapi.controller.CidadeControllerOpenApi;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping(path= "/cidades")
public class CidadeController implements CidadeControllerOpenApi{

	private final CadastroCidadeService cidadeService;
	private final CidadeInputDisassembler cidadeInputDissasembler;
	private final CidadeDtoAssembler cidadeDtoAssembler;

	public CidadeController(CadastroCidadeService cidadeService, 
							CidadeInputDisassembler cidadeInputDissasembler,
							CidadeDtoAssembler cidadeDtoAssembler) {
		this.cidadeService = cidadeService;
		this.cidadeInputDissasembler = cidadeInputDissasembler;
		this.cidadeDtoAssembler = cidadeDtoAssembler;
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
	public CollectionModel<CidadeDto> listar() {

		List<CidadeDto> cidadesDto = cidadeDtoAssembler.toCollectionDto(cidadeService.listar());
		
		cidadesDto.forEach( cidadeDto ->
		{
			cidadeDto.add(WebMvcLinkBuilder.linkTo(
					  WebMvcLinkBuilder.methodOn(CidadeController.class) 
				      .buscar(cidadeDto.getId())).withSelfRel()
				     );

		cidadeDto.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CidadeController.class)
				.listar()).withRel("cidades"));


		cidadeDto.getEstado().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EstadoController.class)
		.buscar(cidadeDto.getEstado().getId())).withSelfRel());
			
		}
	
				);
		
		CollectionModel<CidadeDto> collectionCidadeDto = CollectionModel.of(cidadesDto);
		
		// adicionado link no final .../cidades
		collectionCidadeDto.add(WebMvcLinkBuilder.linkTo(CidadeController.class).withSelfRel());
		
		return collectionCidadeDto;

	}
    
	@Override
 	@GetMapping(path="/{cidadeId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public CidadeDto buscar(  @PathVariable Long cidadeId) {

		CidadeDto cidadeDto =  cidadeDtoAssembler.toDto(cidadeService.buscarOuFalhar(cidadeId));
		
		// metodo DEPRECATED
		//cidadeDto.add(new Link("http://localhost:8080/cidades/1"));
		

		cidadeDto.add(WebMvcLinkBuilder.linkTo(
					  WebMvcLinkBuilder.methodOn(CidadeController.class) 
				      .buscar(cidadeDto.getId())).withSelfRel()
				     );

		
//		cidadeDto.add(WebMvcLinkBuilder.linkTo(CidadeController.class)
//				.slash(cidadeDto.getId())
//				.withSelfRel());

//cidadeDto.add(Link.of("http://localhost:8080/cidades/1"));
//		cidadeDto.add(Link.of("http://localhost:8080/cidades/1", IanaLinkRelations.SELF));
		

		cidadeDto.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CidadeController.class)
				.listar()).withRel("cidades"));
		
//cidadeDto.add(WebMvcLinkBuilder.linkTo(CidadeController.class)
//.withRel("cidades"));
		
//cidadeDto.add(Link.of("http://localhost:8080/cidades", "cidades"));
//cidadeDto.add(Link.of("http://localhost:8080/cidades", IanaLinkRelations.COLLECTION));


		cidadeDto.getEstado().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EstadoController.class)
		.buscar(cidadeDto.getEstado().getId())).withSelfRel());
		
//cidadeDto.getEstado().add(WebMvcLinkBuilder.linkTo(EstadoController.class)
//.slash(cidadeDto.getEstado().getId())
//.withSelfRel());
		
		
//cidadeDto.getEstado().add(Link.of("http://localhost:8080/estados/1"));	
//cidadeDto.add(Link.of("http://localhost:8080/estados", "estados"));

		
		return cidadeDto;

	}

	@Override
 	@PostMapping( produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeDto adicionar( @RequestBody @Valid CidadeInput cidadeInput) {

		try {

			Cidade cidade = cidadeInputDissasembler.toEntity(cidadeInput);
			
			CidadeDto cidadeDto = cidadeDtoAssembler.toDto(cidadeService.salvar(cidade));
			
			ResourceUriHelper.addUriResponseHeader(cidadeDto.getId());
			
//			URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
//			.path("/{id}")
//			.buildAndExpand(cidadeDto.getId()).toUri();
			
//			HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse(); 
			
//			response.setHeader(HttpHeaders.LOCATION, uri.toString());
			
			return cidadeDto;

		} catch (EstadoNaoEncontradoException e) {

			throw new NegocioException(e.getMessage(), e);
		}

	}

	@Override
	@PutMapping(path="/{cidadeId}",produces = MediaType.APPLICATION_JSON_VALUE)
	public CidadeDto atualizar( @PathVariable Long cidadeId, @RequestBody @Valid CidadeInput cidadeInput) {

		Cidade cidade = cidadeService.buscarOuFalhar(cidadeId);
		
		cidadeInputDissasembler.copyToEntity(cidadeInput, cidade);
		
		try {
			
			return cidadeDtoAssembler.toDto(cidadeService.salvar(cidade));

		} catch (EstadoNaoEncontradoException e) {
			
			throw new NegocioException(e.getMessage(), e);
		}

	}

	@Override
 	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(  @PathVariable Long cidadeId) {

		cidadeService.excluir(cidadeId);
		
		return;
	}

}

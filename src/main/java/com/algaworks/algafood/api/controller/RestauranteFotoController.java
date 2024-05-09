package com.algaworks.algafood.api.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.algafood.api.assembler.FotoProdutoDtoAssembler;
import com.algaworks.algafood.api.dto.FotoProdutoDto;
import com.algaworks.algafood.api.input.FotoProdutoInput;
import com.algaworks.algafood.api.openapi.controller.RestauranteProdutoFotoControllerOpenApi;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CatalogoFotoProdutoService;
import com.algaworks.algafood.domain.service.FotoStorageService;
import com.algaworks.algafood.domain.service.FotoStorageService.FotoRecuperada;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteFotoController implements RestauranteProdutoFotoControllerOpenApi  {
	
	
	private final CatalogoFotoProdutoService catalogoFotoProdutoService;
	
	private final CadastroProdutoService cadastroProdutoService;
	
	private final FotoProdutoDtoAssembler fotoProdutoDtoAssembler;
	
	private final FotoStorageService fotoStorageService;

	
 	public RestauranteFotoController(CatalogoFotoProdutoService catalogoFotoProdutoService,
									 CadastroProdutoService cadastroProdutoService,
									 FotoProdutoDtoAssembler fotoProdutoDtoAssembler,
									 FotoStorageService fotoStorageService) {
		this.catalogoFotoProdutoService = catalogoFotoProdutoService;
		this.cadastroProdutoService = cadastroProdutoService;
		this.fotoProdutoDtoAssembler = fotoProdutoDtoAssembler;
		this.fotoStorageService = fotoStorageService;

 
	}


 	@Override
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public FotoProdutoDto atualizarFoto(@PathVariable Long restauranteId,
							  @PathVariable Long produtoId,
							  @Valid FotoProdutoInput fotoProdutoInput,
							  @RequestPart(required=true) MultipartFile arquivo) throws IOException {
		
		Produto produto = cadastroProdutoService.buscarProdutoRestaurante(restauranteId, produtoId);
		
		// ja vem no request
		//MultipartFile arquivo = fotoProdutoInput.getArquivo();
		
		FotoProduto foto = new FotoProduto();
		foto.setProduto(produto);
		foto.setDescricao(fotoProdutoInput.getDescricao());
		foto.setContentType(arquivo.getContentType());
		foto.setTamanho(arquivo.getSize());
		foto.setNomeArquivo(arquivo.getOriginalFilename());
		
		FotoProduto fotoSalva = catalogoFotoProdutoService.salvar(foto, arquivo.getInputStream());
		
		return fotoProdutoDtoAssembler.toModel(fotoSalva);
		
	}
	// Accept = application/json
	// Accept = application/json AND Accept = image/*
 	@Override
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public FotoProdutoDto  consultarFoto(@PathVariable Long restauranteId,
							  @PathVariable Long produtoId){
		
	    // debug on
		//System.out.println("DEBUG 0000 ====== >> PASSEI AQUI" );
		// debug off
		
		FotoProduto fotoProduto = catalogoFotoProdutoService.buscarOuFalhar(restauranteId, produtoId);
		
		return fotoProdutoDtoAssembler.toModel(fotoProduto);
		
	}

	// Accept = image/*
	// not defined
 	@Override
	@GetMapping(produces = MediaType.ALL_VALUE)
	public ResponseEntity<?>  servirFoto(@PathVariable Long restauranteId,
							                               @PathVariable Long produtoId,
							                               @RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException{
		try {
			
	    // debug on
		//System.out.println("DEBUG 0001 ====== >> acceptHeader = " + acceptHeader );
		// debug off
			
		FotoProduto fotoProduto = catalogoFotoProdutoService.buscarOuFalhar(restauranteId, produtoId);
		
	    // debug on
		//System.out.println("DEBUG 0002 ====== >> fotoProduto.getContentType() = " + fotoProduto.getContentType() );
		// debug off
		
		MediaType  mediaTypeFoto = MediaType.parseMediaType(fotoProduto.getContentType());
		
		List<MediaType> mediaTypeAceitas = MediaType.parseMediaTypes(acceptHeader);
		
		verificarCompatibilidadeMediaType(mediaTypeFoto,mediaTypeAceitas);
		
		FotoRecuperada fotoRecuperada = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());
		
		if(fotoRecuperada.temUrl()) 
			{
			return  ResponseEntity
					.status(HttpStatus.FOUND)
					.header(HttpHeaders.LOCATION, fotoRecuperada.getUrl())
					.build();
			}
		else
			{
			return ResponseEntity
					.ok()
					.contentType(mediaTypeFoto)
					.body(new InputStreamResource(fotoRecuperada.getInputStream()));
			}
		}
		catch(EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}
			
		
		
	}
	
 	@Override
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluirFoto(@PathVariable Long restauranteId,
							@PathVariable Long produtoId)
	{
		FotoProduto fotoProduto = catalogoFotoProdutoService.buscarOuFalhar(restauranteId, produtoId);
		catalogoFotoProdutoService.excluir(fotoProduto);
	}



	private void verificarCompatibilidadeMediaType(MediaType mediaTypeFoto, List<MediaType> mediaTypeAceitas) throws HttpMediaTypeNotAcceptableException 
	{
		
	    // debug on
		//System.out.println("DEBUG 0003 ====== >> mediaTypeFoto.getType() = " + mediaTypeFoto.getType() );
		// debug off

		// debug on
		//for (MediaType mt : mediaTypeAceitas) {
		//    
		//	 System.out.println("DEBUG 0004 ====== >> for each mt.getType() = " + mt.getType() );
		//	
		//}
		// debug off
		
		boolean compativel = mediaTypeAceitas.stream()
				.anyMatch(mediaTypeAceita -> mediaTypeAceita.isCompatibleWith(mediaTypeFoto));
		
		if(!compativel) {
			throw new HttpMediaTypeNotAcceptableException(mediaTypeAceitas);
		}
		
	}


	@Override
	public FotoProdutoDto buscar(Long id) {
		return null;
	}


	@Override
	public CollectionModel<FotoProdutoDto> listar() {
		return null;
	}


	@Override
	public PagedModel<FotoProdutoDto> listarPaged(Pageable pageable) {
		return null;
	}
	
	
}

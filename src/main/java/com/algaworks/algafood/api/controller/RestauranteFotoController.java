package com.algaworks.algafood.api.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.algafood.api.assembler.FotoProdutoDtoAssembler;
import com.algaworks.algafood.api.dto.FotoProdutoDto;
import com.algaworks.algafood.api.input.FotoProdutoInput;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CatalogoFotoProdutoService;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteFotoController {
	
	
	private final CatalogoFotoProdutoService catalogoFotoProdutoService;
	
	private final CadastroProdutoService cadastroProdutoService;
	
	private final FotoProdutoDtoAssembler fotoProdutoDtoAssembler;
	
 	public RestauranteFotoController(CatalogoFotoProdutoService catalogoFotoProdutoService,
									 CadastroProdutoService cadastroProdutoService,
									 FotoProdutoDtoAssembler fotoProdutoDtoAssembler) {
		this.catalogoFotoProdutoService = catalogoFotoProdutoService;
		this.cadastroProdutoService = cadastroProdutoService;
		this.fotoProdutoDtoAssembler = fotoProdutoDtoAssembler;

 
	}



	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FotoProdutoDto atualizarFoto(@PathVariable Long restauranteId,
							  @PathVariable Long produtoId,
							  @Valid FotoProdutoInput fotoProdutoInput) throws IOException {
		
		Produto produto = cadastroProdutoService.buscarProdutoRestaurante(restauranteId, produtoId);
		
		MultipartFile arquivo = fotoProdutoInput.getArquivo();
		FotoProduto foto = new FotoProduto();
		foto.setProduto(produto);
		foto.setDescricao(fotoProdutoInput.getDescricao());
		foto.setContentType(arquivo.getContentType());
		foto.setTamanho(arquivo.getSize());
		foto.setNomeArquivo(arquivo.getOriginalFilename());
		
		FotoProduto fotoSalva = catalogoFotoProdutoService.salvar(foto, arquivo.getInputStream());
		
		return fotoProdutoDtoAssembler.toDto(fotoSalva);
		
	}
	
	@GetMapping
	public FotoProdutoDto  consultarFoto(@PathVariable Long restauranteId,
							  @PathVariable Long produtoId){
		
		FotoProduto fotoProduto = catalogoFotoProdutoService.buscarOuFalhar(restauranteId, produtoId);
		
		return fotoProdutoDtoAssembler.toDto(fotoProduto);
		
	}

}

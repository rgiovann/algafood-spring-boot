package com.algaworks.algafood.api.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.input.FotoProdutoInput;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteFotoController {
	
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void atualizarFoto(@PathVariable Long restauranteId,
							  @PathVariable Long produtoId,
							  @Valid FotoProdutoInput fotoProdutoInput) {
		
		var nomeArquivo = UUID.randomUUID().toString() + "_"+  fotoProdutoInput.getArquivo().getOriginalFilename();
		
		var arquivoFotoPath    = Path.of("\\JAVA\\ALGAWORKS_ESR\\Projeto\\algafood-api-catalogo",nomeArquivo);
				
//		System.out.println(arquivoFotoPath);
//		System.out.println(fotoProdutoInput.getArquivo().getContentType());
//		System.out.println(fotoProdutoInput.getDescricao());
//		System.out.println(arquivo.getSize());
//		System.out.println(arquivoFotoPath.toAbsolutePath()); // Print the absolute path

 		
		try {
 			Files.createDirectories(arquivoFotoPath.getParent());
 			fotoProdutoInput.getArquivo().transferTo(arquivoFotoPath);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}  
		
	}

}

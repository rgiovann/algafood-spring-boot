package com.algaworks.algafood.domain.service;

import java.io.InputStream;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.FotoNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.FotoStorageService.NovaFoto;

@Service
public class CatalogoFotoProdutoService {

	private final ProdutoRepository produtoRepository;

	private final FotoStorageService fotoStorageService;

	public CatalogoFotoProdutoService(ProdutoRepository produtoRepository, FotoStorageService fotoStorageService) {
		this.produtoRepository = produtoRepository;
		this.fotoStorageService = fotoStorageService;
	}

	@Transactional
	public FotoProduto salvar(FotoProduto foto, InputStream dadosArquivo) {

		Long restauranteId = foto.getRestauranteId();
		Long produtoId = foto.getProduto().getId();

		String novoNomeArquivo = fotoStorageService.gerarNomeArquivo(foto.getNomeArquivo());
		
		String nomeArquivoExistente = null;

		foto.setNomeArquivo(novoNomeArquivo);

		Optional<FotoProduto> fotoExistente = produtoRepository.findFotoById(restauranteId, produtoId);

		if (fotoExistente.isPresent()) {
			
			nomeArquivoExistente = fotoExistente.get().getNomeArquivo();
			produtoRepository.delete(fotoExistente.get());
		}

		foto = produtoRepository.save(foto);
		produtoRepository.flush(); // salva no BD os dados da foto, commitando a insercao ANTES de salvar a foto.

		NovaFoto novaFoto = NovaFoto.builder().nomeArquivo(novoNomeArquivo).inputStream(dadosArquivo).build();

		fotoStorageService.substituir(nomeArquivoExistente, novaFoto);
 
		return foto;

	}

	public FotoProduto buscarOuFalhar(Long restauranteId, Long produtoId) {
		
		return  produtoRepository.findFotoById(restauranteId,produtoId)
				.orElseThrow(() -> new FotoNaoEncontradaException(produtoId,restauranteId) );
	}

}

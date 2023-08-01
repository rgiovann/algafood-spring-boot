package com.algaworks.algafood.infraestructure.service.storage;

import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.util.FileCopyUtils;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.FotoStorageService;


public class LocalFotoStorageService implements FotoStorageService {

	private StorageProperties storageProperties;

	public LocalFotoStorageService(StorageProperties storageProperties) {
		this.storageProperties = storageProperties;
	}

	@Override
	public void armazenar(NovaFoto novaFoto) {
		try {
			Path arquivoPath = getArquivoPath(novaFoto.getNomeArquivo());
			FileCopyUtils.copy(novaFoto.getInputStream(), Files.newOutputStream(arquivoPath));
		} catch (Exception e) {

			throw new StorageException("Não foi possível armazenar o arquivo " + novaFoto.getNomeArquivo() + ".", e);
		}

	}

	@Override
	public void remover(String nomeArquivo) {
		Path arquivoPath = this.getArquivoPath(nomeArquivo);

		try {
			Files.deleteIfExists(arquivoPath);
		} catch (Exception e) {

			throw new StorageException("Não foi possível excluir o arquivo " + arquivoPath.getFileName() + ".", e);

		}

	}

	@Override
	public FotoRecuperada recuperar(String nomeArquivo) {
		Path arquivoPath = this.getArquivoPath(nomeArquivo);

		try {		
		FotoRecuperada fotoRecuperada = FotoRecuperada.builder()
				                        .inputStream(Files.newInputStream(arquivoPath)).build();

		return fotoRecuperada;

		} catch (Exception e) {
			throw new StorageException("Não foi possível recuperar o arquivo " + arquivoPath.getFileName() + ".", e);
		}
	}

	private Path getArquivoPath(String nomeArquivo) {
		return storageProperties.getLocal().getDiretorioFotos().resolve(Path.of(nomeArquivo));
	}
}

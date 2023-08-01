package com.algaworks.algafood.infraestructure.service.storage;

import java.io.InputStream;

import org.springframework.stereotype.Service;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.FotoStorageService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class S3FotoStorageService implements FotoStorageService{
	
	private AmazonS3 amazonS3;
	private StorageProperties storageProperties;
	
	

	public S3FotoStorageService(AmazonS3 amazonS3, StorageProperties storageProperties) {
		this.amazonS3 = amazonS3;
		this.storageProperties = storageProperties;
	}

	@Override
	public void armazenar(NovaFoto novaFoto) {
		try {
		var objectMetadata = new ObjectMetadata();
		
		objectMetadata.setContentType(novaFoto.getContentType());
		
		var putObjectRequest = new PutObjectRequest(storageProperties.getS3().getBucket(), 
												    getCaminhoArquivo(novaFoto.getNomeArquivo() ), 
												    novaFoto.getInputStream(), objectMetadata)
													.withCannedAcl(CannedAccessControlList.PublicRead);
		amazonS3.putObject(putObjectRequest);
		} catch (Exception e) {
			throw new StorageException("Não foi possível enviar arquivo para Amazon S3", e);
		}
	}

	@Override
	public void remover(String nomeArquivo) {
		try {
			
			System.out.println("*** arquivo " + nomeArquivo);
			
		var deleteObjectRequest = new DeleteObjectRequest(storageProperties.getS3().getBucket(), 
														  getCaminhoArquivo(nomeArquivo));
		amazonS3.deleteObject(deleteObjectRequest);
		} 
		catch (Exception e) {
			throw new StorageException("Não foi possível enviar arquivo para Amazon S3", e);
		}
		
	}

	@Override
	public InputStream recuperar(String nomeArquivo) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	private String getCaminhoArquivo( String nomeArquivo) {
		String caminhoArquivo = String.format("%s/%s", this.storageProperties.getS3().getDiretorioFotos(),nomeArquivo);
		return caminhoArquivo;
	}

}

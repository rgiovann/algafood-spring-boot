package com.algaworks.algafood.core.validation;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

public class FileContentTypeValidator implements ConstraintValidator<FileContentType,MultipartFile> {
	
	private String[] contentFileType;
	
	@Override
	public void initialize(FileContentType constraintAnnotation) {
		contentFileType = constraintAnnotation.allowed();
  	}
 
	@Override
	public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
		
		boolean tipoValido =  multipartFile == null || Arrays.asList(contentFileType).contains(multipartFile.getContentType())  ;
 
		return tipoValido;
	}

}

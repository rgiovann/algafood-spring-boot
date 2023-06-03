package com.algaworks.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
	ENTIDADE_NAO_ENCONTRADA("entidade-nao-encontrada","Entidade não encontrada"),
	PROBLEMA_NA_REQUISICAO("problema-na-requisicao","Violação de regra de negócio"),
	MENSAGEM_CORROMPIDA("mensagem-corrompida","Mensagem corrompida"),
	ENTIDADE_EM_USO("entidade-em-uso","Entidade em uso"),
	PARAMETRO_INVALIDO("parametro-invalido","Parâmetro inválido");


	private String title;
	private String uri;
	
	private ProblemType(String path, String title) {
		this.title = title;
		this.uri = "https://algafood.com.br/" + path;
	}
	
	
}

package com.algaworks.algafood.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonInclude(Include.NON_NULL)
@Builder
@ApiModel("Problema")
public class Problem {
	
	@ApiModelProperty(value="HTTP status",example="400",position =1)
	private Integer status;
	@ApiModelProperty(value="Time stamp do problema",example="2022-12-01T18:09:02.70844Z",position =5)
	private OffsetDateTime timestamp;
	@ApiModelProperty(value="HTTP status (link)",example="https://algafood.com.br/dados-invalidos",position =10)	
	private String type;
	@ApiModelProperty(value="Título problema",example="Dados inválidos",position =15)	
	private String title;
	@ApiModelProperty(value="Detalhes do problema",example="Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.",position =20)	
	private String detail;
	@ApiModelProperty(value="Mensagem para o usuário",example="Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.",position =25)	
	private String userMessage;
	@ApiModelProperty(value="Lista de objetos ou campos que geraram o erro (opcional)",position =30)	
	private List<Field> objects;

	@Getter
	@Builder
	@ApiModel("CampoProblema")
	public static class Field {
		@ApiModelProperty(value="Nome do objeto ou campo", example = "preco")
		private String name;
		@ApiModelProperty(value="Mensagem para o usuário",example="O preço é obrigatório")	
		private String userMessage;
	}
}

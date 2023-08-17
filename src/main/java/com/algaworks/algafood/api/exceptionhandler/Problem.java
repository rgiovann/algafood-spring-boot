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
	
	@ApiModelProperty(value="HTTP status",example="400")
	private Integer status;
	@ApiModelProperty(value="Time stamp do problema",example="2023-12-01T18:09:02.70844Z")
	private OffsetDateTime timestamp;
	@ApiModelProperty(value="HTTP status",example="400")	
	private String type;
	private String title;
	private String detail;
	
	private String userMessage;
	private List<Field> objects;

	@Getter
	@Builder
	@ApiModel("CampoProblema")
	public static class Field {
		private String name;
		private String userMessage;
	}
}

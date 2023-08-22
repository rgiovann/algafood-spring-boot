package com.algaworks.algafood.api.openapi.model;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagedModelOpenApi<T> {
	private List<T> content;
	@ApiModelProperty(value="Quantidade de elementos por página (default é 10)",example="10")
	private Long size;
	@ApiModelProperty(value="Quantidade total elementos",example="50")
	private Long totalElements;
	@ApiModelProperty(value="Quantidade total de páginas",example="5")
	private Long totalPages;
	@ApiModelProperty(value="Número da página (começa em 0)",example="0")	
	private Long number;

}

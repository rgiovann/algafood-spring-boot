package com.algaworks.algafood.api.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;

public interface ControllerInterface<M>
{
	M buscar(Long id);
	
	CollectionModel<M> listar();
	
	PagedModel<M> listarPaged(Pageable pageable);

}

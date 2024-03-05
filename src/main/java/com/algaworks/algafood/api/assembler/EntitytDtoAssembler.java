package com.algaworks.algafood.api.assembler;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import lombok.Getter;

@Getter
public abstract class EntitytDtoAssembler<M extends RepresentationModel<M>, D, C> 
extends RepresentationModelAssemblerSupport<D,M > 
{
	
	 
	private final ModelMapper mapper;
	private  Class<M> dtoRepresentationObject=null;
	private  Class<C> controllerObject=null;;

	
	@SuppressWarnings("unchecked")
	public EntitytDtoAssembler(ModelMapper mapper,
			                   Class<M>dtoRepresentationObject,
			                   Class<C> controllerObject  ) {
		super(controllerObject,dtoRepresentationObject);
		ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
		this.mapper = mapper;
		this.dtoRepresentationObject = (Class<M>) type.getActualTypeArguments()[0];

	}
	
	public M toDto(D entityObject) {
		return  this.mapper.map(entityObject, this.dtoRepresentationObject);
	}
	
 
	public List<M> toCollectionDto(Collection<D> listOfEntityObjects) {
		return listOfEntityObjects.stream().map(o -> this.toDto(o)).collect(Collectors.toList());
	}
	
}

package com.algaworks.algafood.api.assembler;

import java.lang.reflect.ParameterizedType;
 
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.algaworks.algafood.api.controller.ControllerInterface;
import com.algaworks.algafood.api.dto.DtoInterface;

import lombok.Getter;

@Getter
public abstract class EntitytDtoAssembler<M extends RepresentationModel<M> & DtoInterface<M>, D, C extends ControllerInterface<M>> 
extends RepresentationModelAssemblerSupport<D,M > 
{
	
	 
	private final ModelMapper mapper;
	private  Class<M> dtoRepresentationObject=null;
	private  Class<C> controllerRepresentationObject=null;;
	private  Class<D> entityClass=null;;

	
	@SuppressWarnings("unchecked")
	public EntitytDtoAssembler(ModelMapper mapper,
			                   Class<M>dtoRepresentationObject,
			                   Class<C> controllerObject  ) {
		super(controllerObject,dtoRepresentationObject);
		ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
		this.mapper = mapper;
		this.dtoRepresentationObject = (Class<M>) type.getActualTypeArguments()[0];
		this.entityClass = (Class<D>) type.getActualTypeArguments()[1];
		this.controllerRepresentationObject = (Class<C>) type.getActualTypeArguments()[2];


	}
	
	public M toDto(D entityObject) {
		M dtoObject =  this.mapper.map(entityObject, this.dtoRepresentationObject);
		
	    dtoObject.add( linkTo( methodOn(controllerRepresentationObject).buscar(dtoObject.getId()))
	    		               .withSelfRel());	
	    dtoObject.add( linkTo( methodOn(controllerRepresentationObject).listar(  ))
	    		               .withRel((entityClass.getName()+"s").toLowerCase()));		

		return dtoObject;
	}
	
 
	public List<M> toCollectionDto(Collection<D> listOfEntityObjects) {
		return listOfEntityObjects.stream().map(o -> this.toDto(o)).collect(Collectors.toList());
	}
	
}

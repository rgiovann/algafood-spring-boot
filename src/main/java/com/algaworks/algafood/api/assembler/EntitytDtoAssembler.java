package com.algaworks.algafood.api.assembler;

import java.lang.reflect.ParameterizedType;
 
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import com.algaworks.algafood.infraestructure.configuration.Mapper;

import lombok.Getter;

@Getter
public abstract class EntitytDtoAssembler<M extends RepresentationModel<M>, D > 
{
	
	 
	private final Mapper mapper;
	private  Class<M> dtoRepresentationObject=null;
	private  Class<D> entityClass=null;
    private  List<Link> linkList;

	@SuppressWarnings("unchecked")
	public EntitytDtoAssembler(Mapper mapper,
			                   Class<M> dtoRepresentationObject,
			                   List<Link> linkList)
	{
		super();
		ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
		this.mapper = mapper;
		this.dtoRepresentationObject = (Class<M>) type.getActualTypeArguments()[0];
        this.linkList = linkList;
	}
	
	public M toDto(D entityObject) {
		M dtoObject =  this.mapper.map(entityObject, this.dtoRepresentationObject);		
		for (Link link : linkList) {
			dtoObject.add(link); // add links to object
			}   
		return dtoObject;
	}
	
 
	public List<M> toCollectionDto(Collection<D> listOfEntityObjects) {
        return listOfEntityObjects.stream().map(this::toDto).collect(Collectors.toList());
	}
	
}

package com.algaworks.algafood.api.assembler;

import java.lang.reflect.ParameterizedType;
 
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import com.algaworks.algafood.infraestructure.configuration.Mapper;

import lombok.Getter;

@Getter
public abstract class EntitytDtoAssembler<M extends RepresentationModel<M>, D > 
{
	
	 
	private final Mapper mapper;
	private  Class<M> dtoRepresentationObject;
    private  List<Link> linkList;
    private  Link collectionLink;

	@SuppressWarnings("unchecked")
	public EntitytDtoAssembler(Mapper mapper,
			                   Class<M> dtoRepresentationObject,
			                   List<Link> linkList,
			                   Link collectionLink)
	{
		super();
		ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
		this.mapper = mapper;
		this.dtoRepresentationObject = (Class<M>) type.getActualTypeArguments()[0];
        this.linkList = linkList;
        this.collectionLink = collectionLink;
	}
	
	public M toDto(D entityObject) {
		M dtoObject =  this.mapper.map(entityObject, this.dtoRepresentationObject);		
		for (Link link : linkList) {
			dtoObject.add(link); // add links to object
			}   
		return dtoObject;
	}
	
 
	public CollectionModel<M> toCollectionDto(Collection<D> listOfEntityObjects) {
		List<M> listOfDtos = listOfEntityObjects.stream().map(this::toDto).collect(Collectors.toList());
		
		CollectionModel<M> collectionDto =  CollectionModel.of(listOfDtos);
		
		collectionDto.add(collectionLink.withSelfRel());
		
		return  collectionDto;
	}
	
}

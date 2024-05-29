package com.algaworks.algafood.api.assembler;

import java.lang.reflect.ParameterizedType;
 
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import com.algaworks.algafood.infraestructure.configuration.Mapper;

import lombok.Getter;

@Getter
//public abstract class EntitytDtoAssembler<M extends RepresentationModel<M>, D >
public abstract class EntitytDtoAssembler<M extends RepresentationModel<M>, D, C > 
                      extends RepresentationModelAssemblerSupport<D, M> 
 
{
	private final Mapper mapper;
	private  Class<M> dtoRepresentationObject;
	private  Class<C> controllerRepresentationObject;
    private  List<Link> linkList;
    private  Link collectionLink;

	@SuppressWarnings("unchecked")
	public EntitytDtoAssembler(Mapper mapper,Class<C> controllerClass,Class<M> dtoClass)
	{
		super(controllerClass, dtoClass);
		ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
		this.mapper = mapper;
		this.dtoRepresentationObject = (Class<M>) type.getActualTypeArguments()[0];

	}
	
	@Override   // RepresentationModelAssembler method interface
	public M toModel(D entityObject) {
		M dtoObject =  this.mapper.map(entityObject, this.dtoRepresentationObject);
		this.linkList = constructLinks(entityObject);
		for (Link link : linkList) {
			dtoObject.add(link); // add links to object
			}   
		return dtoObject;
	}
	
	// o problema é o collectionLink atributo que é nulo.
	//	public CollectionModel<D> toCollectionModel(Iterable<? extends D> entities) {
	//		return this.map(entities).toResources();
	//	}
	
	@Override // RepresentationModelAssembler method interface
 	public CollectionModel<M> toCollectionModel(Iterable<? extends D>  listOfEntityObjects) {
		//List<M> listOfDtos = listOfEntityObjects.stream().map(this::toModel).collect(Collectors.toList());
		//CollectionModel<M> collectionDto =  CollectionModel.of(listOfDtos);
		//collectionDto.add( constructCollectionLinks());
		return super.toCollectionModel(listOfEntityObjects).add(constructCollectionLinks());
		//return  collectionDto;
	}
	
	
	public abstract List<Link> constructLinks(D entityObject) ; 
	
	public abstract Link constructCollectionLinks() ; 

	
}

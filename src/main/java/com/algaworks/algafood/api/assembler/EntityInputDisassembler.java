package com.algaworks.algafood.api.assembler;

import java.lang.reflect.ParameterizedType;

import org.modelmapper.ModelMapper;

import lombok.Getter;

@Getter
public abstract class EntityInputDisassembler<I, D> {
 

	private final ModelMapper mapper;
	private final Class<D> domainObject;
		
	@SuppressWarnings("unchecked")
	public EntityInputDisassembler(ModelMapper mapper) {
		// The line ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass(); 
		// is needed to get the generic type of the class EntityInputDisassembler. 
		// The generic type of a class is the type of the object that is being used to instantiate the class. 
		// In this case, the generic type of the EntityInputDisassembler class is <I, D>, 
		// where I is the type of the input object and D is the type of the domain object.
		ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
		this.mapper = new ModelMapper();
		
		// The line this.domainObject = (Class<D>) type.getActualTypeArguments()[1]; assigns the type 
		// of the domain object to the domainObject field. The getActualTypeArguments() method of the 
		// ParameterizedType class returns an array of the actual type arguments of the parameterized type.
		// In this case, the array will have two elements, the first element being the type of the input 
		// object and the second element being the type of the domain object.
		this.domainObject = (Class<D>) type.getActualTypeArguments()[1];
	}
	
	public D toEntity(I inputObject) {
		return this.mapper.map(inputObject, this.domainObject);
	}
	
	public void copyToEntity(I inputObject, D domainObject) {
		mapper.map(inputObject, domainObject);
	}
	
}
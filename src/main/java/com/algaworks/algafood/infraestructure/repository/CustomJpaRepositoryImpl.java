package com.algaworks.algafood.infraestructure.repository;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.repository.CustomJpaRepository;

public class CustomJpaRepositoryImpl<T,ID> extends SimpleJpaRepository<T, ID> implements CustomJpaRepository<T, ID>{
	
	private EntityManager manager;

	public CustomJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.manager = entityManager;

	}

	@Override
	public Optional<T> buscarPrimeiro() {
		String jpql= "from " + getDomainClass().getName(); 
		
		try {
			T entity = manager.createQuery(jpql,getDomainClass())
						 .setMaxResults(1)
						 .getSingleResult();
			return Optional.ofNullable(entity);

		}
		catch (NoResultException e ) {
			throw new RestauranteNaoEncontradoException("Não encontrado o 1o restaurante, banco de dados está vazio.");
		}

	}

	@Override
	public void detach(T entity) {
		manager.detach(entity);
		
	}

}

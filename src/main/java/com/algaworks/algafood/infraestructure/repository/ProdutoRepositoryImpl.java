package com.algaworks.algafood.infraestructure.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepositoryQueries;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepositoryQueries{

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	@Transactional
	public FotoProduto save(FotoProduto foto) {
		// TODO Auto-generated method stub
		return manager.merge(foto);
	}

	@Override
	@Transactional
	public void delete(FotoProduto foto) {
		manager.remove(foto);		
	}

}

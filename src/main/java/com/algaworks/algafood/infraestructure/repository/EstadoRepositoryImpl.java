package com.algaworks.algafood.infraestructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Repository
public class EstadoRepositoryImpl implements EstadoRepository{

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Estado> listar(){
		
		return manager.createQuery("from Estado", Estado.class).getResultList();
				
	}
	
	@Override
	@Transactional
	public Estado salvar(Estado estado) {
		return manager.merge(estado);
	}
	
	@Override
 	public Estado buscar(Long id) {
		return manager.find(Estado.class, id);
	}
 	
	@Override
 	@Transactional
 	public void remover(Long id) {
		Estado estado = this.buscar( id);
 		if( estado == null) {
 			throw new EmptyResultDataAccessException(1);
 		}
 		manager.remove(estado);
 	}

}

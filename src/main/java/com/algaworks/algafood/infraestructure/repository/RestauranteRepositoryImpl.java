package com.algaworks.algafood.infraestructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepository{

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Restaurante> listar(){
		
		return manager.createQuery("from Restaurante", Restaurante.class).getResultList();
				
	}
	
	@Override
	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		return manager.merge(restaurante);
	}
	
	@Override
 	public Restaurante buscar(Long id) {
		return manager.find(Restaurante.class, id);
	}
 	
	@Override
 	@Transactional
 	public void remover(Restaurante cozinha) {
 		cozinha = this.buscar(cozinha.getId());
 		manager.remove(cozinha);
 	}

}

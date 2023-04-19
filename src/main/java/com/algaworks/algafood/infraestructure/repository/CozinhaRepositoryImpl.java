//package com.algaworks.algafood.infraestructure.repository;
//
//import java.util.List;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.transaction.Transactional;
//
//import org.springframework.dao.EmptyResultDataAccessException;
//import org.springframework.stereotype.Repository;
//
//import com.algaworks.algafood.domain.model.Cozinha;
//import com.algaworks.algafood.domain.repository.CozinhaRepository;
//
//@Repository
//public class CozinhaRepositoryImpl implements CozinhaRepository{
//
//	@PersistenceContext
//	private EntityManager manager;
//	
//	@Override
//	public List<Cozinha> listar(){
//		
//		return manager.createQuery("from Cozinha", Cozinha.class).getResultList();
//				
//	}
//	
//	@Override
//	@Transactional
//	public Cozinha salvar(Cozinha cozinha) {
//		return manager.merge(cozinha);
//	}
//	
//	@Override
// 	public Cozinha buscar(Long id) {
//		return manager.find(Cozinha.class, id);
//	}
// 	
//	@Override
// 	@Transactional
// 	public void remover(Long id) {
// 		Cozinha cozinha = this.buscar( id);
// 		if( cozinha == null) {
// 			throw new EmptyResultDataAccessException(1);
// 		}
// 		manager.remove(cozinha);
// 	}
//
//	@Override
//	public List<Cozinha> consultarPorNome(String nomeCozinha) {
// 		return manager.createQuery("from Cozinha where nome like :nome", Cozinha.class)
// 				.setParameter("nome","%" + nomeCozinha + "%")
// 				.getResultList();
//	}
//
//}
//

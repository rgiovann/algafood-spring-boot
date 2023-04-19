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
//import com.algaworks.algafood.domain.model.Cidade;
//import com.algaworks.algafood.domain.repository.CidadeRepository;
//
//@Repository
//public class CidadeRepositoryImpl implements CidadeRepository{
//
//	@PersistenceContext
//	private EntityManager manager;
//	
//	@Override
//	public List<Cidade> listar(){
//		
//		return manager.createQuery("from Cidade", Cidade.class).getResultList();
//				
//	}
//	
//	@Override
//	@Transactional
//	public Cidade salvar(Cidade cidade) {
//		return manager.merge(cidade);
//	}
//	
//	@Override
// 	public Cidade buscar(Long id) {
//		return manager.find(Cidade.class, id);
//	}
// 	
//	@Override
// 	@Transactional
// 	public void remover(Long id) {
//		Cidade cidade = this.buscar( id);
// 		if( cidade == null) {
// 			throw new EmptyResultDataAccessException(1);
// 		}
// 		manager.remove(cidade);
// 	}
//
//}

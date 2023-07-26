package com.algaworks.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.algaworks.algafood.api.dto.VendaDiaria;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.Pedido;

public interface PedidoRepository extends CustomJpaRepository<Pedido, Long>,
                                          JpaSpecificationExecutor<Pedido>{
	
// 	@Query("from Pedido p join fetch p.itens join fetch p.cliente join fetch p.restaurante r join fetch r.cozinha")
// 	List<Pedido> findAll();
	
// 	@Query("from Pedido p join p.itens join p.cliente join  p.restaurante r join  r.cozinha")
// 	List<Pedido> findAll();
	

// https://app.algaworks.com/forum/topicos/89167/qual-a-diferenca-entre-join-fetch-e-join#104048	
// https://vladmihalcea.com/hibernate-multiplebagfetchexception/
// join sem fetch em @OnetoMany itens. Vai fazer um select de itens / pedido
//	@Query("from Pedido p " +
//			"join p.cliente " +
//			"join p.restaurante r " +
//			"join r.cozinha " +
//			"join p.itens i " +
//			"join i.produto")
//	List<Pedido> findAll();

// https://app.algaworks.com/forum/topicos/89167/qual-a-diferenca-entre-join-fetch-e-join#104048	
// join com fetch em @OnetoMany itens (caso o DTO contenha a lista de itens). Vai fazer um select apenas
//	@Query("from Pedido p " +
//			"join p.cliente " +
//			"join p.restaurante r " +
//			"join r.cozinha " +
//			"join fetch p.itens i " +
//			"join i.produto")
//	List<Pedido> findAll();
	
	
	// QUERY FINDALL OFICILA (SEM SPECS) 
	@Query("from Pedido p " +
	       " join fetch p.cliente " +
		   " join fetch p.restaurante r" +
	       " join fetch r.cozinha")
	List<Pedido> findAll();
 	
	// JPA já monta findBy<atributo>
	Optional<Pedido> findByCodigo(String codigo);
		
}

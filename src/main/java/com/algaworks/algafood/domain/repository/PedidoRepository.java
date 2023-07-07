package com.algaworks.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

import com.algaworks.algafood.domain.model.Pedido;

public interface PedidoRepository extends CustomJpaRepository<Pedido, Long>{
	
// 	@Query("from Pedido p join fetch p.itens join fetch p.cliente join fetch p.restaurante r join fetch r.cozinha")
// 	List<Pedido> findAll();
	
 	@Query("from Pedido p join p.itens join p.cliente join   p.restaurante r join   r.cozinha")
 	List<Pedido> findAll();
 	
	// JPA já monta findBy<atributo>
	Optional<Pedido> findByCodigo(String codigo);

	
}

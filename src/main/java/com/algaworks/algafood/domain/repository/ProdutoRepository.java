package com.algaworks.algafood.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.algaworks.algafood.domain.model.Produto;

public interface ProdutoRepository extends  JpaRepository<Produto,Long>{
	

	// minha solucao, usa inner join, mais custosa.
//	@Query("SELECT obj FROM Produto obj INNER JOIN obj.restaurante rest "
//		  	 + "WHERE rest.id = :restauranteId AND obj.id = :produtoId")
//	 Optional<Produto> buscaProdutoRestaurante(Long restauranteId, Long produtoId );
	
	   
    @Query("SELECT obj FROM Produto obj WHERE obj.restaurante.id = :restauranteId AND obj.id = :produtoId")
    Optional<Produto> buscaProdutoRestaurante(@Param("restauranteId") Long restauranteId, @Param("produtoId") Long produtoId);

   
}

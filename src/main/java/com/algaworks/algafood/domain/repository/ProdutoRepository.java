package com.algaworks.algafood.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.algaworks.algafood.domain.model.Produto;

public interface ProdutoRepository extends  JpaRepository<Produto,Long>{
	
 
	@Query("SELECT obj FROM Produto obj INNER JOIN obj.restaurante rest "
		  	 + "WHERE rest.id = :restauranteId AND obj.id = :produtoId")
	 Optional<Produto> buscaProdutoRestaurante(Long restauranteId, Long produtoId );
	
  
}

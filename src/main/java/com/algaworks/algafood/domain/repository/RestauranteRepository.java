package com.algaworks.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.algaworks.algafood.domain.model.Restaurante;

// AGORA RESTAURANTE TEM UM REPOSITORIO CUSTOMIZADO
public interface RestauranteRepository extends CustomJpaRepository<Restaurante, Long>,
                                       RestauranteRepositoryQueries,
                                       JpaSpecificationExecutor<Restaurante>{
	
	// 
	@Query("from Restaurante r join fetch r.cozinha")
//	@Query("from Restaurante r join fetch r.cozinha order by r.id")

	List<Restaurante> findAll();
	
	
	//TESTE
	List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);
	
//	@Query("from Restaurante where nome like %:nome% and cozinha.id = :id")
	List<Restaurante> consultarPorNome(String nome, @Param("id") Long cozinha);
	
	// esse findCustomizado vai ser instanciado pelo Spring pois implementei o método em 
	// CozinhaRepositoryImpl
	List<Restaurante> findCustomizado(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);

}

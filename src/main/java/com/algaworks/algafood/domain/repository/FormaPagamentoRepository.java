package com.algaworks.algafood.domain.repository;

import java.time.OffsetDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.algaworks.algafood.domain.model.FormaPagamento;

public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento,Long>{
	
	// jpql syntax
	@Query("SELECT MAX(dataAtualizacao) FROM FormaPagamento")
	OffsetDateTime getDataUltimaAtualizacao();
	

}

package com.algaworks.algafood.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.algafood.domain.model.Cozinha;

// You are extending JpaRepository<T, ID> interface, it means that spring boot must autoconfigure this repository 
// bean for you, namely, it will be configured a proxy bean of SimpleJpaRepository<T, ID>.
// In simple words, we do not just create a bean using @Repository or @Component annotation, 
// we extend the spring-data interface and then our repository bean will be autoconfigured.
// https://stackoverflow.com/questions/75066907/why-get-warning-in-repository-unnecessary-repository


public interface CozinhaRepository extends JpaRepository<Cozinha,Long>{
	
	
	//List<Cozinha> findTodasByNome(String cozinha);
	
	List<Cozinha> findTodasByNomeContaining(String cozinha);


	

}

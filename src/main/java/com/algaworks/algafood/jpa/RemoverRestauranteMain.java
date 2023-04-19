//package com.algaworks.algafood.jpa;
//
//import org.springframework.boot.WebApplicationType;
//import org.springframework.boot.builder.SpringApplicationBuilder;
//import org.springframework.context.ApplicationContext;
//
//import com.algaworks.algafood.AlgafoodApiApplication;
//import com.algaworks.algafood.domain.model.Restaurante;
//import com.algaworks.algafood.domain.repository.RestauranteRepository;
//
//public class RemoverRestauranteMain {
//	
//	/*
//	 Quando você usa o Boot Dashboard ele vai inicializar a aplicação com base na classe anotada 
//	 com @SpringBootApplication. Para o exemplo da aula, é necessário executar a classe ConsultaCozinhaMain, 
//	 pra que ele use a configuração feita manualmente. 
//	 */
//	
//	public static void main(String[] args) {
//		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
//				.web(WebApplicationType.NONE)
//				.run(args);
//				
//				RestauranteRepository cozinhaRepository = applicationContext.getBean(RestauranteRepository.class);
//				
//				Restaurante cozinha = new Restaurante();
//				cozinha.setId(1L);
//				cozinhaRepository.remover(1L);
//				
//
// 
//	}
//
//}

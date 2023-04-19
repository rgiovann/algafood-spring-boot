//package com.algaworks.algafood.jpa;
//
//import org.springframework.boot.WebApplicationType;
//import org.springframework.boot.builder.SpringApplicationBuilder;
//import org.springframework.context.ApplicationContext;
//
//import com.algaworks.algafood.AlgafoodApiApplication;
//import com.algaworks.algafood.domain.model.Cozinha;
//import com.algaworks.algafood.domain.repository.CozinhaRepository;
//
//public class atualizarCozinhaMain {
//
//	/*
//	 * Quando você usa o Boot Dashboard ele vai inicializar a aplicação com base na
//	 * classe anotada com @SpringBootApplication. Para o exemplo da aula, é
//	 * necessário executar a classe ConsultaCozinhaMain, pra que ele use a
//	 * configuração feita manualmente.
//	 */
//
//	public static void main(String[] args) {
//		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
//				.web(WebApplicationType.NONE).run(args);
//
//		CozinhaRepository cozinhaRepository = applicationContext.getBean(CozinhaRepository.class);
//
//		Cozinha cozinha = new Cozinha();
//		cozinha.setId(1L);
//		cozinha.setNome("Brasileira");
//		System.out.println(cozinhaRepository.salvar(cozinha));
//
//	}
//
//}

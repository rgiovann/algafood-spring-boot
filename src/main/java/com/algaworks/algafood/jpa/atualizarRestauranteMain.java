//package com.algaworks.algafood.jpa;
//
//import java.math.BigDecimal;
//
//import org.springframework.boot.WebApplicationType;
//import org.springframework.boot.builder.SpringApplicationBuilder;
//import org.springframework.context.ApplicationContext;
//
//import com.algaworks.algafood.AlgafoodApiApplication;
//import com.algaworks.algafood.domain.model.Restaurante;
//import com.algaworks.algafood.domain.repository.RestauranteRepository;
//
//public class atualizarRestauranteMain {
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
//		RestauranteRepository restauranteRepository = applicationContext.getBean(RestauranteRepository.class);
//
//		Restaurante restaurante = new Restaurante();
//		restaurante.setId(1L);
//		restaurante.setNome("Restaurante e Churrascaria Fogo de Chão");
//		restaurante.setTaxaFrete(new BigDecimal(35.0));
//		System.out.println(restauranteRepository.salvar(restaurante));
//
//	}
//
//}

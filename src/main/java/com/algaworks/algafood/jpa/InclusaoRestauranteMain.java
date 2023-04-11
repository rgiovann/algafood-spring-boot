package com.algaworks.algafood.jpa;

import java.math.BigDecimal;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

public class InclusaoRestauranteMain {
	
	/*
	 Quando você usa o Boot Dashboard ele vai inicializar a aplicação com base na classe anotada 
	 com @SpringBootApplication. Para o exemplo da aula, é necessário executar a classe ConsultaCozinhaMain, 
	 pra que ele use a configuração feita manualmente. 
	 */
	
	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
				
		RestauranteRepository restauranteRepository = applicationContext.getBean(RestauranteRepository.class);
				
				Restaurante restaurante1 = new Restaurante();
				restaurante1.setNome("Restaurante Boi Dourado");
				restaurante1.setTaxaFrete(new BigDecimal(11.5));

				
				Restaurante restaurante2 = new Restaurante();
				restaurante2.setNome("Sushi Open Seas");
				restaurante2.setTaxaFrete(new BigDecimal(21.60));	
				
				System.out.println(restauranteRepository.salvar(restaurante1));

				System.out.println(restauranteRepository.salvar(restaurante2));
 
	}

}

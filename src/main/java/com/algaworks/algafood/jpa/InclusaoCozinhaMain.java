package com.algaworks.algafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

public class InclusaoCozinhaMain {
	
	/*
	 Quando você usa o Boot Dashboard ele vai inicializar a aplicação com base na classe anotada 
	 com @SpringBootApplication. Para o exemplo da aula, é necessário executar a classe ConsultaCozinhaMain, 
	 pra que ele use a configuração feita manualmente. 
	 */
	
	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
				
		CozinhaRepository cozinhaRepository = applicationContext.getBean(CozinhaRepository.class);
				
				Cozinha cozinha1 = new Cozinha();
				cozinha1.setNome("Brasileira");
				
				Cozinha cozinha2 = new Cozinha();
				cozinha2.setNome("Japonesa");
				
				System.out.println(cozinhaRepository.salvar(cozinha1));

				System.out.println(cozinhaRepository.salvar(cozinha2));
 
	}

}

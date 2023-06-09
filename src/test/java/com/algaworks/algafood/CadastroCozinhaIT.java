package com.algaworks.algafood;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.flywaydb.core.Flyway;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.util.DatabaseCleaner;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroCozinhaIT {
	
	@Autowired
	private Flyway flyway;
	
	@Autowired
	DatabaseCleaner databaseCleaner;
	
	@Autowired
	CozinhaRepository cozinhaRepository;
	
	@LocalServerPort
	private int port;
	
	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath= "/cozinhas";
		
		flyway.migrate();
		
		databaseCleaner.clearTables();
		
		prepararDados();

	}
	
	@Test
	public void deveRetornarStatus200_QuandoConsultarCozinhas() {

			given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveConterQuatroCozinhas_QuandoConsultarCozinhas() {

		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("",Matchers.hasSize(2)) 
			.body("nome",Matchers.hasItems("Americana","Tailandesa"));
	}
	
	
	@Test
	public void deveRetornarStatus201_QuandoCadastrarNovaCozinha() {

			given()
			.body("{ \"nome\": \"Chinesa\" }")
			.accept(ContentType.JSON)
			.contentType(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());

	}
	
	@Test
	public void deveRetornarRespostaeEstatus200_QuandoConsultarCozinhaExistente() {

			given()
			.pathParam("cozinhaId", 2)
			.accept(ContentType.JSON)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome",equalTo("Americana"));

	}
	
	@Test
	public void deveRetornarRespostaeEstatus404_QuandoConsultarCozinhaInexistente() {

			given()
			.pathParam("cozinhaId", 100)
			.accept(ContentType.JSON)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	
	private void prepararDados() {
		Cozinha  cozinha1 = new Cozinha();
		cozinha1.setNome("Tailandesa");
		cozinhaRepository.save(cozinha1);
		Cozinha  cozinha2= new Cozinha();
		cozinha2.setNome("Americana");
		cozinhaRepository.save(cozinha2);		
	}
	
// ***** TESTES DE INTEGRACAO SEM API	
//	@Autowired
//	private CadastroCozinhaService cadastroCozinhaService;

//	@Test
//	public void testarCadastroCozinhaComSucesso() {
//		// cenario
//		CozinhaDto novaCozinhaDto = new CozinhaDto();
//		novaCozinhaDto.setNome("Chinesa");
//		
//		//acao
//		
//		novaCozinhaDto = cadastroCozinhaService.salvar(novaCozinhaDto);
//				
//		//validacao
//		
//		assertThat(novaCozinhaDto).isNotNull();
//		assertThat(novaCozinhaDto.getId()).isNotNull();
//
//		
//	}
//	
//	@Test
//	public void testarCadastroCozinhaSemNomeDeveGerarException() {
//		// cenario
//		CozinhaDto novaCozinha = new CozinhaDto();
//		novaCozinha.setNome(null);
//
//		//acao // validacao
//		ConstraintViolationException erroEsperado =
//				Assertions.assertThrows(ConstraintViolationException.class, () -> {
//					cadastroCozinhaService.salvar(novaCozinha);
//				});
//
//		assertThat(erroEsperado).isNotNull();
//	}
//	
//	@Test
//	public void testarExclusaoCozinhaEmUsoDeveGerarException() {
//		
//		// cenario
//		//checar se cozinha existe...
//		cadastroCozinhaService.buscarOuFalhar(1L);
//		
//
//		//acao // validacao
//		EntidadeEmUsoException erroEsperado =
//				Assertions.assertThrows(EntidadeEmUsoException.class, () -> {
//					cadastroCozinhaService.excluir(1L);
//				});
//
//		assertThat(erroEsperado).isNotNull();
//	}
//	
//	
//	@Test
//	public void testarExclusaoCozinhaInexistenteDeveGerarException() {
//		
//
//		//acao // validacao
//		CozinhaNaoEncontradaException erroEsperado =
//				Assertions.assertThrows(CozinhaNaoEncontradaException.class, () -> {
//					cadastroCozinhaService.excluir(100L);
//				});
//
//		assertThat(erroEsperado).isNotNull();
//	}


}

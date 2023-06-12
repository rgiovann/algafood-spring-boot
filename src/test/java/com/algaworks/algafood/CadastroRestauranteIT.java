package com.algaworks.algafood;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.math.BigDecimal;

import org.flywaydb.core.Flyway;
import org.hamcrest.Matchers;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.util.DatabaseCleaner;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroRestauranteIT {

	private static final String COZINHA_POPULAR  = "Tailandesa";

    private static final String COZINHA_INDIANA = "Indiana";
    
	private Long idRestauranteCentral;
	
	private static final String RESTAURANTE_ID_INVALIDO = "3a";

	private static final Integer RESTAURANTE_ID_INEXISTENTE = 100;
	
	private static final Integer COZINHA_ID_INEXISTENTE = 100;

	private static final Integer QTDADE_RESTAURANTES =2;

	private static final BigDecimal TAXA_FRETE_RESTAURANTE_UNIVERSITARIO = BigDecimal.valueOf(4.50);

	private static final BigDecimal TAXA_FRETE_RESTAURANTE_CENTRAL = BigDecimal.valueOf(10.10);

	private static final String RESTAURANTE_UNIVERSITARIO = "Restaurante Universitário";

	private static final String RESTAURANTE_CENTRAL = "Restaurante Central";

	private static final String JSON_RESTAURANTE = ResourceUtils.getContentFromResource("json/cadastro_de_restaurante.json");
	
	private static final String JSON_RESTAURANTE_PARCIAL = ResourceUtils.getContentFromResource("json/cadastro_de_restaurante_parcial.json");
	
	private static final String JSON_RESTAURANTE_EXCEPTION_PARCIAL_COZINHA_INEXISTENTE = ResourceUtils.getContentFromResource("json/cadastro_de_restaurante_parcial_cozinha_inexistente.json");

	private static final String JSON_RESTAURANTE_EXCEPTION_PROP_INVALIDA = ResourceUtils.getContentFromResource("json/cadastro_de_restaurante_unrecognizedpropertyexception.json");
	
	private static final String JSON_RESTAURANTE_EXCEPTION_PROP_IGNORADA = ResourceUtils.getContentFromResource("json/cadastro_de_restaurante_ignoredpropertyexception.json");
	
	private static final String JSON_RESTAURANTE_EXCEPTION_FORMATO_INVALIDO = ResourceUtils.getContentFromResource("json/cadastro_de_restaurante_invalidformatexception.json");
	
	private static final String JSON_RESTAURANTE_EXCEPTION_JSON_CORROMPIDO = ResourceUtils.getContentFromResource("json/cadastro_de_restaurante_httpmessagenotreadable.json");

	private static final String JSON_RESTAURANTE_EXCEPTION_COZINHA_INEXISTENTE = ResourceUtils.getContentFromResource("json/cadastro_de_restaurante_cozinha_inexistente.json");

	private static final String EXCEPTION_TITULO_MSG_RECURSO_INEXISTENTE= "Recurso não encontrado";
	
	private static final String EXCEPTION_DETALHE_MSG_RECURSO_INEXISTENTE= "Restaurante de código "+ RESTAURANTE_ID_INEXISTENTE.toString() +" não encontrado.";
	
	private static final String EXCEPTION_TITULO_MSG_CORROMPIDA = "Mensagem corrompida";
	
	private static final String EXCEPTION_DETALHE_MSG_CORROMPIDA_PROP_INVALIDA = "A propriedade 'taxaFretee' não é reconhecida para a entidade 'RestauranteDto'.";

	private static final String EXCEPTION_DETALHE_MSG_CORROMPIDA_PROP_IGNORADA = "A propriedade 'endereco' da entidade 'RestauranteDto' deve ser ignorada no corpo da requisição.";

	private static final String EXCEPTION_DETALHE_MSG_CORROMPIDA_FORMATO_INVALIDO = "A propriedade 'taxaFrete' recebeu o valor 'S0' que é do tipo inválido. Corrija e informe o valor compatível com o tipo BigDecimal";
	
	private static final String EXCEPTION_DETALHE_MSG_CORROMPIDA_JSON_CORROMPIDO = "O corpo da requisição está corrompido, não é possivel fazer o parsing da mensagem Json. Verifique a sintaxe da mensagem.";

	private static final String EXCEPTION_TITULO_MSG_PARAMETRO_INVALIDO = "Parâmetro inválido";

	private static final String EXCEPTION_DETALHE_PARAMETRO_INVALIDO = "O parâmetro de URL 'restauranteId' recebeu o valor '3a' que é de um tipo inválido. Corrija e informe o valor compatível com o tipo Long";

	private static final String EXCEPTION_TITULO_MSG_VIOLACAO_REGRA_NEGOCIO = "Violação de regra de negócio";
	
	private static final String EXCEPTION_DETALHE_MSG_VIOLACAO_REGRA_NEGOCIO_COZINHA_NAO_EXISTE = "Cozinha de código "+ COZINHA_ID_INEXISTENTE.toString() + " não encontrada.";


	@Autowired
	private Flyway flyway;

	@Autowired
	DatabaseCleaner databaseCleaner;

	@Autowired
	CozinhaRepository cozinhaRepository;
	
	@Autowired
	RestauranteRepository restauranteRepository;

	@LocalServerPort
	private int port;

	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/restaurantes";

		flyway.migrate();

		databaseCleaner.clearTables();

		prepararDados();

	}
	
	
	//************************************************
	// (GET) CONSULTAS RESTAURANTE  
	//************************************************
	
	//                  *** SUCESSO ***
 	@Test
	public void deveRetornarStatus200_QuandoConsultarRestaurantes() {

		given().accept(ContentType.JSON)
		.when().get()
		.then().statusCode(HttpStatus.OK.value());
	}

	@Test
	public void deveConterDoisRestaurantes_QuandoConsultarRestaurantes() {

		given().accept(ContentType.JSON)
		.when().get()
		.then().body("", Matchers.hasSize(QTDADE_RESTAURANTES)).body("nome",
				Matchers.hasItems(RESTAURANTE_UNIVERSITARIO,RESTAURANTE_CENTRAL));
	}
	
	@Test
	public void deveRetornarHttpStatus200_QuandoConsultarRestauranteExistente() {

		given().pathParam("restauranteId", idRestauranteCentral).accept(ContentType.JSON)
		.when().get("/{restauranteId}")
		.then().statusCode(HttpStatus.OK.value()).body("nome", equalTo(RESTAURANTE_CENTRAL));

	}
	
	//                  *** EXCEPTIONS ***


	@Test
	public void deveRetornarHttpStatus404_QuandoConsultarRestauranteInexistente() {

		given().pathParam("cozinhaId", RESTAURANTE_ID_INEXISTENTE).accept(ContentType.JSON)
		.when().get("/{cozinhaId}")
		.then().statusCode(HttpStatus.NOT_FOUND.value())		
		.body("title", equalTo( EXCEPTION_TITULO_MSG_RECURSO_INEXISTENTE))
		.and()
		.body("detail", equalTo(  EXCEPTION_DETALHE_MSG_RECURSO_INEXISTENTE)); 
	}
	
	@Test
	public void deveRetornarHttpStatus400_QuandoConsultarRestauranteComParametroInvalido() {

		given().pathParam("cozinhaId", RESTAURANTE_ID_INVALIDO).accept(ContentType.JSON)
		.when().get("/{cozinhaId}")
		.then().statusCode(HttpStatus.BAD_REQUEST.value())
		.body("title", equalTo( EXCEPTION_TITULO_MSG_PARAMETRO_INVALIDO))
		.and()
		.body("detail", equalTo(  EXCEPTION_DETALHE_PARAMETRO_INVALIDO)); 
	}
	
	
	//************************************************
	// (POST) CADASTRO NOVO RESTAURANTE  
	//************************************************	
	
	//                  *** SUCESSO ***

	@Test
	public void deveRetornarHttpStatus201_QuandoCadastrarNovoRestaurante() throws JSONException {
		JSONObject objJson = new JSONObject(JSON_RESTAURANTE);
 		given().body(JSON_RESTAURANTE).accept(ContentType.JSON).contentType(ContentType.JSON)
		.when().post()
		.then()
			.statusCode(HttpStatus.CREATED.value())
			.body("nome", equalTo(objJson.getString("nome")))
			.and()
			.body("taxaFrete", equalTo(  (float) objJson.getDouble("taxaFrete") )) 
 			.and()
 			.body("cozinhaId", equalTo(    objJson.getInt("cozinhaId") ));

 	}
	
	//                  *** EXCEPTIONS ***
	
	
	@Test
	public void deveRetornarStatus400_QuandoCadastrarNovoRestauranteComCozinhaInexistente()   {
 		given().body(JSON_RESTAURANTE_EXCEPTION_COZINHA_INEXISTENTE).accept(ContentType.JSON).contentType(ContentType.JSON)
		.when().post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("title", equalTo( EXCEPTION_TITULO_MSG_VIOLACAO_REGRA_NEGOCIO))
			.and()
			.body("detail", equalTo(  EXCEPTION_DETALHE_MSG_VIOLACAO_REGRA_NEGOCIO_COZINHA_NAO_EXISTE)); 

 	}
	
	@Test
	public void deveRetornarStatus400_QuandoCadastrarNovoRestauranteComJsonCorrompido()   {
 		given().body(JSON_RESTAURANTE_EXCEPTION_JSON_CORROMPIDO).accept(ContentType.JSON).contentType(ContentType.JSON)
		.when().post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("title", equalTo( EXCEPTION_TITULO_MSG_CORROMPIDA))
			.and()
			.body("detail", equalTo(  EXCEPTION_DETALHE_MSG_CORROMPIDA_JSON_CORROMPIDO)); 

 	}
	
	@Test
	public void deveRetornarStatus400_QuandoCadastrarNovoRestauranteComPropriedadeInvalida()   {
 		given().body(JSON_RESTAURANTE_EXCEPTION_PROP_INVALIDA).accept(ContentType.JSON).contentType(ContentType.JSON)
		.when().post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("title", equalTo( EXCEPTION_TITULO_MSG_CORROMPIDA))
			.and()
			.body("detail", equalTo(  EXCEPTION_DETALHE_MSG_CORROMPIDA_PROP_INVALIDA)); 


 	}
	
	@Test
	public void deveRetornarHttpStatus400_QuandoCadastrarNovoRestauranteComPropriedadeIgnorada()   {
 		given().body(JSON_RESTAURANTE_EXCEPTION_PROP_IGNORADA).accept(ContentType.JSON).contentType(ContentType.JSON)
		.when().post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("title", equalTo( EXCEPTION_TITULO_MSG_CORROMPIDA))
			.and()
			.body("detail", equalTo(  EXCEPTION_DETALHE_MSG_CORROMPIDA_PROP_IGNORADA)); 


 	}
	
	@Test
	public void deveRetornarHttpStatus400_QuandoCadastrarNovoRestauranteComFormatoInvalido() {
 		given().body(JSON_RESTAURANTE_EXCEPTION_FORMATO_INVALIDO).accept(ContentType.JSON).contentType(ContentType.JSON)
		.when().post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("title", equalTo( EXCEPTION_TITULO_MSG_CORROMPIDA))
			.and()
			.body("detail", equalTo(  EXCEPTION_DETALHE_MSG_CORROMPIDA_FORMATO_INVALIDO)); 


 	}
	
	//************************************************
	// (POST) ALTERACAO  RESTAURANTE  
	//************************************************	
	
	//                  *** SUCESSO ***
	
	
	@Test
	public void deveRetornarHttpStatus200_QuandoAlterarRestaurante() throws JSONException {
		JSONObject objJson = new JSONObject(JSON_RESTAURANTE);
		given().pathParam("cozinhaId",idRestauranteCentral ).accept(ContentType.JSON)
 		.body(JSON_RESTAURANTE).accept(ContentType.JSON).contentType(ContentType.JSON)
		.when().put("/{cozinhaId}")
		.then().statusCode(HttpStatus.OK.value())
		.body("nome", equalTo(objJson.getString("nome")))
		.and()
		.body("taxaFrete", equalTo(  (float) objJson.getDouble("taxaFrete") )) 
		.and()
		.body("cozinhaId", equalTo(   idRestauranteCentral.intValue()     )); 
	}
	  
	//                *** EXCEPTION ***
	
	@Test
	public void deveRetornarStatus400_QuandoAlterarNovoRestauranteComJsonCorrompido()   {
 		given().pathParam("cozinhaId", idRestauranteCentral).accept(ContentType.JSON)
 		.body(JSON_RESTAURANTE_EXCEPTION_JSON_CORROMPIDO).accept(ContentType.JSON).contentType(ContentType.JSON)
		.when().put("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("title", equalTo( EXCEPTION_TITULO_MSG_CORROMPIDA))
			.and()
			.body("detail", equalTo(  EXCEPTION_DETALHE_MSG_CORROMPIDA_JSON_CORROMPIDO)); 

 	}
	
	@Test
	public void deveRetornarHttpStatus404_QuandoAlterarRestauranteInexistente() {

		given().pathParam("cozinhaId", RESTAURANTE_ID_INEXISTENTE).accept(ContentType.JSON)
 		.body(JSON_RESTAURANTE).accept(ContentType.JSON).contentType(ContentType.JSON)
		.when().put("/{cozinhaId}")
		.then().statusCode(HttpStatus.NOT_FOUND.value())		
		.body("title", equalTo( EXCEPTION_TITULO_MSG_RECURSO_INEXISTENTE))
		.and()
		.body("detail", equalTo(  EXCEPTION_DETALHE_MSG_RECURSO_INEXISTENTE)); 
	}
	
	@Test
	public void deveRetornarHttpStatus404_QuandoAlterarRestauranteComCozinhaInexistente() {

		given().pathParam("cozinhaId", idRestauranteCentral).accept(ContentType.JSON)
 		.body(JSON_RESTAURANTE_EXCEPTION_COZINHA_INEXISTENTE).accept(ContentType.JSON).contentType(ContentType.JSON)
		.when().put("/{cozinhaId}")
		.then().statusCode(HttpStatus.BAD_REQUEST.value())		
		.body("title", equalTo( EXCEPTION_TITULO_MSG_VIOLACAO_REGRA_NEGOCIO))
		.and()
		.body("detail", equalTo(  EXCEPTION_DETALHE_MSG_VIOLACAO_REGRA_NEGOCIO_COZINHA_NAO_EXISTE)); 
	}
	
	
	
	@Test
	public void deveRetornarHttpStatus400_QuandoAlterarRestauranteComParametroInvalido() {

		given().pathParam("cozinhaId",RESTAURANTE_ID_INVALIDO ).accept(ContentType.JSON)
 		.body(JSON_RESTAURANTE).accept(ContentType.JSON).contentType(ContentType.JSON)
		.when().put("/{cozinhaId}")
		.then().statusCode(HttpStatus.BAD_REQUEST.value())
		.body("title", equalTo( EXCEPTION_TITULO_MSG_PARAMETRO_INVALIDO ))
		.and()
		.body("detail", equalTo( EXCEPTION_DETALHE_PARAMETRO_INVALIDO )); 
	}
	
	
	@Test
	public void deveRetornarHttpStatus400_QuandoAlterarNovoRestauranteComPropriedadeInvalida() {
 		given().pathParam("restauranteId", idRestauranteCentral).accept(ContentType.JSON)
 		.body(JSON_RESTAURANTE_EXCEPTION_PROP_INVALIDA).accept(ContentType.JSON).contentType(ContentType.JSON)
		.when().put("/{restauranteId}")
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("title", equalTo( EXCEPTION_TITULO_MSG_CORROMPIDA))
			.and()
			.body("detail", equalTo(  EXCEPTION_DETALHE_MSG_CORROMPIDA_PROP_INVALIDA)); 

	}
 	
 		@Test
 		public void deveRetornarHttpStatus400_QuandoAlterarNovoRestauranteComPropriedadeIgnorada() {
 	 		given().pathParam("restauranteId", idRestauranteCentral).accept(ContentType.JSON)
 	 		.body(JSON_RESTAURANTE_EXCEPTION_PROP_IGNORADA).accept(ContentType.JSON).contentType(ContentType.JSON)
 			.when().put("/{restauranteId}")
 			.then()
 				.statusCode(HttpStatus.BAD_REQUEST.value())
 				.body("title", equalTo( EXCEPTION_TITULO_MSG_CORROMPIDA))
 				.and()
 				.body("detail", equalTo(  EXCEPTION_DETALHE_MSG_CORROMPIDA_PROP_IGNORADA)); 


 	 	}
 		
 		@Test
 		public void deveRetornarHttpStatus400_QuandoAlterarNovoRestauranteComFormatoInvalido(){
 	 		given().pathParam("restauranteId", idRestauranteCentral).accept(ContentType.JSON)
 	 		.body(JSON_RESTAURANTE_EXCEPTION_FORMATO_INVALIDO).accept(ContentType.JSON).contentType(ContentType.JSON)
 	 		.when().put("/{restauranteId}")
 	 		.then()
 				.statusCode(HttpStatus.BAD_REQUEST.value())
 				.body("title", equalTo( EXCEPTION_TITULO_MSG_CORROMPIDA))
 				.and()
 				.body("detail", equalTo(  EXCEPTION_DETALHE_MSG_CORROMPIDA_FORMATO_INVALIDO)); 


 	 
	
	}

 		//************************************************
 		// (PATCH) ALTERACAO PARCIAL NOVO RESTAURANTE  
 		//************************************************	
 		
 		//                  *** SUCESSO ***
 		
 		
 		@Test
 		public void deveRetornarHttpStatus200_QuandoAlterarParcialRestaurante() throws JSONException {
 			JSONObject objJson = new JSONObject(JSON_RESTAURANTE_PARCIAL);
 			given().pathParam("cozinhaId",idRestauranteCentral ).accept(ContentType.JSON)
 	 		.body(JSON_RESTAURANTE).accept(ContentType.JSON).contentType(ContentType.JSON)
 			.when().patch("/{cozinhaId}")
 			.then().statusCode(HttpStatus.OK.value())

 			.body("taxaFrete", equalTo(  (float) objJson.getDouble("taxaFrete") )); 
 		}
 		  
 		//                *** EXCEPTION ***
 		
 		@Test
 		public void deveRetornarStatus400_QuandoAlterarParcialNovoRestauranteComJsonCorrompido()   {
 	 		given().pathParam("cozinhaId", idRestauranteCentral).accept(ContentType.JSON)
 	 		.body(JSON_RESTAURANTE_EXCEPTION_JSON_CORROMPIDO).accept(ContentType.JSON).contentType(ContentType.JSON)
 			.when().patch("/{cozinhaId}")
 			.then()
 				.statusCode(HttpStatus.BAD_REQUEST.value())
 				.body("title", equalTo( EXCEPTION_TITULO_MSG_CORROMPIDA))
 				.and()
 				.body("detail", equalTo(  EXCEPTION_DETALHE_MSG_CORROMPIDA_JSON_CORROMPIDO)); 

 	 	}
 		
 		@Test
 		public void deveRetornarHttpStatus404_QuandoAlterarParcialRestauranteInexistente() {

 			given().pathParam("cozinhaId", RESTAURANTE_ID_INEXISTENTE).accept(ContentType.JSON)
 	 		.body(JSON_RESTAURANTE).accept(ContentType.JSON).contentType(ContentType.JSON)
 			.when().patch("/{cozinhaId}")
 			.then().statusCode(HttpStatus.NOT_FOUND.value())		
 			.body("title", equalTo( EXCEPTION_TITULO_MSG_RECURSO_INEXISTENTE))
 			.and()
 			.body("detail", equalTo(  EXCEPTION_DETALHE_MSG_RECURSO_INEXISTENTE)); 
 		}
 		
 		@Test
 		public void deveRetornarHttpStatus404_QuandoAlterarParcialRestauranteComCozinhaInexistente() {

 			given().pathParam("cozinhaId", idRestauranteCentral).accept(ContentType.JSON)
 	 		.body(JSON_RESTAURANTE_EXCEPTION_PARCIAL_COZINHA_INEXISTENTE).accept(ContentType.JSON).contentType(ContentType.JSON)
 			.when().patch("/{cozinhaId}")
 			.then().statusCode(HttpStatus.BAD_REQUEST.value())		
 			.body("title", equalTo( EXCEPTION_TITULO_MSG_VIOLACAO_REGRA_NEGOCIO))
 			.and()
 			.body("detail", equalTo(  EXCEPTION_DETALHE_MSG_VIOLACAO_REGRA_NEGOCIO_COZINHA_NAO_EXISTE)); 
 		}
 		
 		
 		
 		@Test
 		public void deveRetornarHttpStatus400_QuandoAlterarParcialRestauranteComParametroInvalido() {

 			given().pathParam("cozinhaId",RESTAURANTE_ID_INVALIDO ).accept(ContentType.JSON)
 	 		.body(JSON_RESTAURANTE).accept(ContentType.JSON).contentType(ContentType.JSON)
 			.when().patch("/{cozinhaId}")
 			.then().statusCode(HttpStatus.BAD_REQUEST.value())
 			.body("title", equalTo( EXCEPTION_TITULO_MSG_PARAMETRO_INVALIDO ))
 			.and()
 			.body("detail", equalTo( EXCEPTION_DETALHE_PARAMETRO_INVALIDO )); 
 		}
 		
 		
 		@Test
 		public void deveRetornarHttpStatus400_QuandoAlterarParcialNovoRestauranteComPropriedadeInvalida() {
 	 		given().pathParam("restauranteId", idRestauranteCentral).accept(ContentType.JSON)
 	 		.body(JSON_RESTAURANTE_EXCEPTION_PROP_INVALIDA).accept(ContentType.JSON).contentType(ContentType.JSON)
 			.when().patch("/{restauranteId}")
 			.then()
 				.statusCode(HttpStatus.BAD_REQUEST.value())
 				.body("title", equalTo( EXCEPTION_TITULO_MSG_CORROMPIDA))
 				.and()
 				.body("detail", equalTo(  EXCEPTION_DETALHE_MSG_CORROMPIDA_PROP_INVALIDA)); 

 		}
 	 	
 	 		@Test
 	 		public void deveRetornarHttpStatus400_QuandoAlterarParcialNovoRestauranteComPropriedadeIgnorada() {
 	 	 		given().pathParam("restauranteId", idRestauranteCentral).accept(ContentType.JSON)
 	 	 		.body(JSON_RESTAURANTE_EXCEPTION_PROP_IGNORADA).accept(ContentType.JSON).contentType(ContentType.JSON)
 	 			.when().patch("/{restauranteId}")
 	 			.then()
 	 				.statusCode(HttpStatus.BAD_REQUEST.value())
 	 				.body("title", equalTo( EXCEPTION_TITULO_MSG_CORROMPIDA))
 	 				.and()
 	 				.body("detail", equalTo(  EXCEPTION_DETALHE_MSG_CORROMPIDA_PROP_IGNORADA)); 


 	 	 	}
 	 		
 	 		@Test
 	 		public void deveRetornarHttpStatus400_QuandoAlterarParcialNovoRestauranteComFormatoInvalido(){
 	 	 		given().pathParam("restauranteId", idRestauranteCentral).accept(ContentType.JSON)
 	 	 		.body(JSON_RESTAURANTE_EXCEPTION_FORMATO_INVALIDO).accept(ContentType.JSON).contentType(ContentType.JSON)
 	 	 		.when().patch("/{restauranteId}")
 	 	 		.then()
 	 				.statusCode(HttpStatus.BAD_REQUEST.value())
 	 				.body("title", equalTo( EXCEPTION_TITULO_MSG_CORROMPIDA))
 	 				.and()
 	 				.body("detail", equalTo(  EXCEPTION_DETALHE_MSG_CORROMPIDA_FORMATO_INVALIDO)); 
 	 		}


	private void prepararDados() {
		
		Cozinha cozinhaIndiana = new Cozinha();
		cozinhaIndiana.setNome(COZINHA_INDIANA);
		cozinhaIndiana = cozinhaRepository.save(cozinhaIndiana);
		
		Cozinha cozinhaPopular = new Cozinha();
		cozinhaPopular.setNome(COZINHA_POPULAR);
		cozinhaPopular = cozinhaRepository.save(cozinhaPopular);
		
		Restaurante restauranteCentral = new Restaurante();
		restauranteCentral.setCozinha(cozinhaIndiana);
		restauranteCentral.setNome(RESTAURANTE_CENTRAL);
		restauranteCentral.setTaxaFrete( TAXA_FRETE_RESTAURANTE_CENTRAL );	
		restauranteRepository.save(restauranteCentral);
		idRestauranteCentral = restauranteCentral.getId();
		
		Restaurante restauranteUniversitario = new Restaurante();
		restauranteUniversitario.setCozinha(cozinhaPopular);
		restauranteUniversitario.setNome(RESTAURANTE_UNIVERSITARIO);
		restauranteUniversitario.setTaxaFrete( TAXA_FRETE_RESTAURANTE_UNIVERSITARIO );
		restauranteRepository.save(restauranteUniversitario);


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

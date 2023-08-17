package com.algaworks.algafood.core.openapi;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.core.openapi.model.PageableModelOpenApi;
import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RepresentationBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.json.JacksonModuleRegistrar;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig implements WebMvcConfigurer{
	
	  @Bean
	  Docket apiDocket() {
		  TypeResolver typeResolver = new TypeResolver();
		  
	    return new Docket(DocumentationType.OAS_30)
	        .select()
	        .apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood.api"))
	        .paths(PathSelectors.any())
	        //.paths(PathSelectors.ant("/restaurantes/*"))
	        .build()
	        .useDefaultResponseMessages(false)
	        // .globalResponseMessage(RequestMethod.GET, globalGetResponseMessages()) - deprecated
	        .globalResponses(HttpMethod.GET, globalGetResponseMessages())
	        .globalResponses(HttpMethod.PUT, globalPutResponseMessages())
	        .globalResponses(HttpMethod.POST, globalPostResponseMessages())
	        .globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())
	        .additionalModels(typeResolver.resolve(Problem.class))
	        .directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
	        .apiInfo(apiInfo())
	        .tags(new Tag("Cidades","Gerencia as cidades"));
		
	  }
	  
	  private List<Response> globalGetResponseMessages() {
		  return Arrays.asList(

		  new ResponseBuilder()
		  .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
		  .description("Erro interno do servidor") // Internal Server Error
		  .representation(MediaType.APPLICATION_JSON)
		  .apply(this::configureModelProblema)
		  .build(),
		  new ResponseBuilder()
		  .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
		  .description("Recurso não possui representação que poderia ser aceita pelo consumidor")
		  .build()
		  );
		  }
	  
	  private List<Response> globalPostResponseMessages() {
		  return Arrays.asList(

		  new ResponseBuilder()
		  .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
		  .description("Requisição enviada pelo cliente está malformada ou contém dados inválidos.") 
		  .representation(MediaType.APPLICATION_JSON)
		  .apply(this::configureModelProblema)
		  .build(),
		  new ResponseBuilder()
		  .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
		  .description("Recurso não possui representação que poderia ser aceita pelo consumidor")
		  .build(),		  
		  new ResponseBuilder()
		  .code(String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()))
		  .description("Servidor não suporta o tipo de mídia da requisição enviada pelo cliente")
		  .representation(MediaType.APPLICATION_JSON)
		  .apply(this::configureModelProblema)
		  .build()
		  );
		  }
	  
	private List<Response> globalPutResponseMessages() {
		  return Arrays.asList(
				  
		  new ResponseBuilder()
		  .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
		  .description("Erro interno do servidor") // Internal Server Error
		  .representation(MediaType.APPLICATION_JSON)
		  .apply(this::configureModelProblema)
		  .build(),
		  new ResponseBuilder()
		  .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
		  .description("Requisição enviada pelo cliente está malformada ou contém dados inválidos.") 
		  .representation(MediaType.APPLICATION_JSON)
		  .apply(this::configureModelProblema)
		  .build(),
		  new ResponseBuilder()
		  .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
		  .description("Recurso não possui representação que poderia ser aceita pelo consumidor")
		  .build(),		  
		  new ResponseBuilder()
		  .code(String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()))
		  .description("Servidor não suporta o tipo de mídia da requisição enviada pelo cliente")
		  .representation(MediaType.APPLICATION_JSON)
		  .apply(this::configureModelProblema)
		  .build()
		  );
		  }
	  
	  private List<Response> globalDeleteResponseMessages() {
		  return Arrays.asList(
				  
		  new ResponseBuilder()
		  .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
		  .description("Erro interno do servidor") // Internal Server Error
		  .representation(MediaType.APPLICATION_JSON)
		  .apply(this::configureModelProblema)
		  .build(),
		  new ResponseBuilder()
		  .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
		  .description("Requisição enviada pelo cliente está malformada ou contém dados inválidos.") 
		  .representation(MediaType.APPLICATION_JSON)
		  .apply(this::configureModelProblema)
		  .build()
		  );
		  }
	  
	  public ApiInfo apiInfo() {
		  return new ApiInfoBuilder()
				  .title("Algafood API")
				  .description("Api aberta para clientes e restaurantes")
				  .version("1")
				  .contact(new Contact("Algaworks", "https://algaworks.com","contato@algaworks.com"))
				  .build();
				  
	  }
	  
		@Bean
		JacksonModuleRegistrar springFoxJacksonConfig() {
			return objectMapper -> objectMapper.registerModule(new JavaTimeModule());
		}
		
		  private void configureModelProblema(RepresentationBuilder representationBuilder) {
			    representationBuilder.model(m -> m.name("Problema")
			                         .referenceModel(ref -> ref.key(k -> k.qualifiedModelName(q -> q.name("Problema")
			                         .namespace("com.algaworks.algafood.api.exceptionhandler")))));
			}
		  
	
}

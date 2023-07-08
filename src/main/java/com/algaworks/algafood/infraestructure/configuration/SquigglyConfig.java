package com.algaworks.algafood.infraestructure.configuration;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;
import com.github.bohnman.squiggly.web.RequestSquigglyContextProvider;
import com.github.bohnman.squiggly.web.SquigglyRequestFilter;

@Configuration
public class SquigglyConfig {

	// esse filtro é processado nas requisições HTTP automaticamente
	// ObjectMapper é uma classe do Jackson
	// RequestSquigglyContextProvider nome do parametro é "fields", pode alterar
	
	// {{host}}/pedidos?fields=codigo,valorTotal,sub*,cliente%5Bnome,id%5D   --> cliente[nome, id]
	
    @Bean
    FilterRegistrationBean<SquigglyRequestFilter> squigglyRequestFilter(ObjectMapper objectMapper ){
		
    	Squiggly.init(objectMapper, new RequestSquigglyContextProvider("campos",null));
    	
    	var urlPatterns = Arrays.asList("/pedidos/*","/restaurantes/*");
    	
		var filterRegistrarion = new FilterRegistrationBean<SquigglyRequestFilter>();
		
		filterRegistrarion.setFilter(new SquigglyRequestFilter());
						
		filterRegistrarion.setOrder(1);
		
		filterRegistrarion.setUrlPatterns(urlPatterns);
		
		return filterRegistrarion;
	}

}

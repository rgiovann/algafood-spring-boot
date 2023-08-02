package com.algaworks.algafood.core.email;

import java.util.Set;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@Validated
@Getter
@Setter
@Component
@ConfigurationProperties("algafood.email")
public class EmailProperties {
	
	@NotNull
	private String remetente;
	
	private SandBox sandBox = new SandBox();
	
	private TipoEmail tipo = TipoEmail.FAKE; 
	
	public enum TipoEmail{
		FAKE, AWS, SANDBOX
	}
	
	@Setter
	@Getter
	@Validated
	public class SandBox 
	{
		@NotNull
		private Set<String> destinatarios;
	}

}

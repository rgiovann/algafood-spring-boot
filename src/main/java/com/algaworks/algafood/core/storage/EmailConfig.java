package com.algaworks.algafood.core.storage;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

import com.algaworks.algafood.core.email.EmailProperties;
import com.algaworks.algafood.core.email.EmailProperties.TipoEmail;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import com.algaworks.algafood.infraestructure.service.email.FakeEnvioEmailService;
import com.algaworks.algafood.infraestructure.service.email.SmtpEnvioEmailService;

@Configuration
public class EmailConfig {
	
	private final EmailProperties emailProperties;
	private final JavaMailSender javaMailSender;
	private final freemarker.template.Configuration freeMarkerConfig;

	
    public EmailConfig(EmailProperties emailProperties,
    				    JavaMailSender javaMailSender,
    				    freemarker.template.Configuration freeMarkerConfig) {
		this.emailProperties = emailProperties;
		this.javaMailSender = javaMailSender;
		this.freeMarkerConfig = freeMarkerConfig;
	}

	@Bean
	EnvioEmailService envioEmailService() {
		if ( TipoEmail.AWS.equals(emailProperties.getTipo()) ){
			return new SmtpEnvioEmailService(javaMailSender, emailProperties, freeMarkerConfig);
		}
		return new FakeEnvioEmailService(emailProperties, freeMarkerConfig);
		
	}

}
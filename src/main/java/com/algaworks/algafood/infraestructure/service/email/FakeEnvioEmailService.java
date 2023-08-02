package com.algaworks.algafood.infraestructure.service.email;

import java.util.Set;

import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.algaworks.algafood.core.email.EmailProperties;
import com.algaworks.algafood.domain.service.EnvioEmailService;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FakeEnvioEmailService implements EnvioEmailService {

	private final EmailProperties emailProperties;

	private final Configuration freeMarkerConfig;

	public FakeEnvioEmailService(EmailProperties emailProperties, Configuration freeMarkerConfig) {
		this.emailProperties = emailProperties;
		this.freeMarkerConfig = freeMarkerConfig;
	}

	@Override
	public void enviar(Mensagem mensagem) {
			int i=1;
		    Set<String> destinatarios = mensagem.getDestinatarios();
			String corpo = processarTemplate(mensagem);
			log.info("************ DISPARO DE EMAIL ************ ");
			log.info("Remetente...........: " + emailProperties.getRemetente());
			log.info("Destinatário(s).....: ");
			for (String destinatario : destinatarios) {
				log.info(String.valueOf(i++)+". " + destinatario );
			}
			log.info("Assunto.............: " + mensagem.getAssunto());	 
			log.info("Corpo...............:\n " + corpo);

	}

	private String processarTemplate(Mensagem mensagem) {
		try {
			Template template = this.freeMarkerConfig.getTemplate(mensagem.getNomeTemplate());

			return FreeMarkerTemplateUtils.processTemplateIntoString(template, mensagem.getVariaveis());

		} catch (Exception e) {
			throw new EmailException("Não foi possível montar o corpo do email", e);
		}
	}

}

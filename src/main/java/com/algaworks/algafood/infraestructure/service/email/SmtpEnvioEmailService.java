package com.algaworks.algafood.infraestructure.service.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.algaworks.algafood.core.email.EmailProperties;
import com.algaworks.algafood.domain.service.EnvioEmailService;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class SmtpEnvioEmailService implements EnvioEmailService {

	private final JavaMailSender mailSender;

	protected final EmailProperties emailProperties;

	private final Configuration freeMarkerConfig;

	public SmtpEnvioEmailService(JavaMailSender mailSender, EmailProperties emailProperties,
			Configuration freeMarkerConfig) {
		this.mailSender = mailSender;
		this.emailProperties = emailProperties;
		this.freeMarkerConfig = freeMarkerConfig;
	}

	@Override
	public void enviar(Mensagem mensagem) {

		try {

			MimeMessage mimeMessage = criarMimeMessage(mensagem);

			this.mailSender.send(mimeMessage);

		} catch (Exception e) {
			throw new EmailException("Não foi possível enviar email", e);
		}
	}

	private String processarTemplate(Mensagem mensagem) {
		try {
			Template template = this.freeMarkerConfig.getTemplate(mensagem.getNomeTemplate());

			return FreeMarkerTemplateUtils.processTemplateIntoString(template, mensagem.getVariaveis());

		} catch (Exception e) {
			throw new EmailException("Não foi possível montar o corpo do email", e);
		}
	}

	protected MimeMessage criarMimeMessage(Mensagem mensagem) throws MessagingException {

		String corpo = processarTemplate(mensagem);

		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
		boolean emailFormatIsHtml = true;

		helper.setFrom(emailProperties.getRemetente());
		helper.setSubject(mensagem.getAssunto());
		helper.setText(corpo, emailFormatIsHtml);
		helper.setTo(mensagem.getDestinatarios().toArray(new String[0]));

		return mimeMessage;
	}

}

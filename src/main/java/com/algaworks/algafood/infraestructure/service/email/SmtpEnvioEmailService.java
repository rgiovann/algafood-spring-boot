package com.algaworks.algafood.infraestructure.service.email;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.core.email.EmailProperties;
import com.algaworks.algafood.domain.service.EnvioEmailService;

@Service
public class SmtpEnvioEmailService implements EnvioEmailService{
	
	private JavaMailSender mailSender;

	private EmailProperties emailProperties;

	public SmtpEnvioEmailService(JavaMailSender mailSender, 
								 EmailProperties emailProperties) {
		this.mailSender = mailSender;
		this.emailProperties = emailProperties;
	}

	@Override
	public void enviar(Mensagem mensagem) {

		
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage(); 									
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,"UTF-8");	
			String tag = emailProperties.getTag();
			String email = emailProperties.getRemetente();
			helper.setFrom(email,tag);
			helper.setSubject(mensagem.getAssunto());
			helper.setText(mensagem.getCorpo(),true);
			helper.setTo(mensagem.getDestinatarios().toArray(new String[0]));
			
			this.mailSender.send(mimeMessage);
			
			
		} catch (Exception e) {
			throw new EmailException("Não foi possível enviar email", e);
		}
	}

}

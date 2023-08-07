package com.algaworks.algafood.infraestructure.service.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.algaworks.algafood.core.email.EmailProperties;

import freemarker.template.Configuration;

public class SandBoxEnvioEmailService extends SmtpEnvioEmailService {

	
	public SandBoxEnvioEmailService(JavaMailSender mailSender, EmailProperties emailProperties,
			Configuration freeMarkerConfig) {
		super(mailSender, emailProperties, freeMarkerConfig);
	}
	
 
    @Override
    protected MimeMessage criarMimeMessage(Mensagem mensagem) throws MessagingException {
        MimeMessage mimeMessage = super.criarMimeMessage(mensagem);
        
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        helper.setTo( getEmailProperties().getSandBox().getDestinatarios().toArray(new String[0]));
        
        return mimeMessage;
    } 

}

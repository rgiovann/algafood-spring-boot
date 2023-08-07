package com.algaworks.algafood.domain.listeners;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.algaworks.algafood.domain.event.PedidoCanceladoEvent;
import com.algaworks.algafood.domain.event.PedidoConfirmadoEvent;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import com.algaworks.algafood.domain.service.EnvioEmailService.Mensagem;

@Component
public class NotificacaoClientePedidoConfirmadoListener {
	
	private final EnvioEmailService envioEmailService;
	
	public NotificacaoClientePedidoConfirmadoListener(EnvioEmailService envioEmailService) {
		this.envioEmailService = envioEmailService;
	}


	// só envia email DEPOIS do commit no BD 
	@TransactionalEventListener
	public void aoConfirmarPedido(PedidoConfirmadoEvent event) {
		Pedido pedido = event.getPedido();
		var mensagem = Mensagem.builder()
		   .assunto(pedido.getRestaurante().getNome() + "- Pedido CONFIRMADO")
		   .nomeTemplate("pedido-confirmado.html")
		   .variavel("pedido", pedido)
		   .destinatario(pedido.getCliente().getEmail())
		   .build();

		this.envioEmailService.enviar(mensagem);
		
	}
	
	
	// só envia email DEPOIS do commit no BD 
	@TransactionalEventListener
	public void aoCancelarPedido(PedidoCanceladoEvent event) {
		Pedido pedido = event.getPedido();
		var mensagem = Mensagem.builder()
		   .assunto(pedido.getRestaurante().getNome() + "- Pedido CANCELADO")
		   .nomeTemplate("pedido-cancelado.html")
		   .variavel("pedido", pedido)
		   .destinatario(pedido.getCliente().getEmail())
		   .build();

		this.envioEmailService.enviar(mensagem);
		
	}

}

package com.algaworks.algafood.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.EnvioEmailService.Mensagem;

@Service
public class FluxoPedidoService {
	
	private CadastroEmissaoPedidoService cadastroEmissaoPedido;
	private EnvioEmailService envioEmailService;
	
	
	public FluxoPedidoService(CadastroEmissaoPedidoService cadastroEmissaoPedido, EnvioEmailService envioEmailService) {
		this.cadastroEmissaoPedido = cadastroEmissaoPedido;
		this.envioEmailService = envioEmailService;
	}

	@Transactional
	public void confirmarPedido(String codigoPedido)
	{
		Pedido pedido = cadastroEmissaoPedido.buscarOuFalhar(codigoPedido);
		
		pedido.confirmar();
		
		var mensagem = Mensagem.builder()
					   .assunto(pedido.getRestaurante().getNome() + "- Pedido Confirmado")
					   .corpo("O pedido <strong>" + pedido.getCodigo() + "</strong> foi confirmado.")
					   .destinatario(pedido.getCliente().getEmail())
					   .build();

		this.envioEmailService.enviar(mensagem);
	}
	
	@Transactional
	public void cancelarPedido(String codigoPedido)
	{
		Pedido pedido = cadastroEmissaoPedido.buscarOuFalhar(codigoPedido);
			
		pedido.cancelar();
		
	}
	
	@Transactional
	public void entregarPedido(String codigoPedido)
	{
		Pedido pedido = cadastroEmissaoPedido.buscarOuFalhar(codigoPedido);
		
		pedido.entregar();
		
	}
	

}

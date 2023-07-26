package com.algaworks.algafood.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.PedidoNaoEncontradoException;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.infraestructure.repository.spec.PedidoSpecs;

@Service
public class CadastroEmissaoPedidoService {

	private final PedidoRepository pedidoRepository;
	private final CadastroCidadeService cadastroCidadeService;
	private final CadastroFormaPagamentoService cadastroFormaPagamentoService;
	private final CadastroRestauranteService cadastroRestauranteService;
	private final CadastroUsuarioService cadastroUsuarioService;
	private final CadastroProdutoService cadastroProdutoService;


	public CadastroEmissaoPedidoService(	PedidoRepository pedidoRepository,
									CadastroCidadeService cadastroCidadeService,
									CadastroFormaPagamentoService cadastroFormaPagamentoService,
									CadastroRestauranteService cadastroRestauranteService,
									CadastroUsuarioService cadastroUsuarioService,
									CadastroProdutoService cadastroProdutoService) {

		this.pedidoRepository = pedidoRepository;
		this.cadastroCidadeService = cadastroCidadeService;
		this.cadastroFormaPagamentoService = cadastroFormaPagamentoService;
		this.cadastroRestauranteService = cadastroRestauranteService;
		this.cadastroUsuarioService = cadastroUsuarioService;
		this.cadastroProdutoService = cadastroProdutoService;

	}

	public Page<Pedido> listar(PedidoFilter filtro, Pageable pageable) {

		return pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro), pageable);

	}

	public Pedido buscarOuFalhar(String pedidoId) {
		return pedidoRepository.findByCodigo(pedidoId).orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));
	}

//	@Transactional
//	public Pedido salvar(Pedido pedido) {
//		{
//			Usuario usuario = cadastroUsuarioService.buscarOuFalhar(1L);
//			FormaPagamento formaPgto = cadastroFormaPagamentoService.buscarOuFalhar(pedido.getFormaPagamento().getId());
//			Cidade cidade = cadastroCidadeService.buscarOuFalhar(pedido.getEnderecoEntrega().getCidade().getId());
//			Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(pedido.getRestaurante().getId());
//			
//			pedido.setCliente(usuario);
//			pedido.setFormaPagamento(formaPgto);
//			pedido.getEnderecoEntrega().setCidade(cidade);
//			pedido.setRestaurante(restaurante);
//			
//			List<ItemPedido> itensPedido = pedido.getItens();
//			
//			Produto produto = null;
//			BigDecimal valorTotalItemPedido = BigDecimal.ZERO;
//			BigDecimal valorTotaPedido = BigDecimal.ZERO;
//			BigDecimal taxaFrete = restaurante.getTaxaFrete();
//
// 
//
//			for(ItemPedido item : itensPedido) {
//				produto = cadastroProdutoService.buscarProdutoRestaurante(1L, item.getProduto().getId());				
//				item.setProduto(produto);
//				item.setPedido(pedido);
// 				valorTotalItemPedido = produto.getPreco().multiply(BigDecimal.valueOf(item.getQuantidade()));
// 				item.setPrecoTotal(valorTotalItemPedido );
//				item.setPrecoUnitario( produto.getPreco());
//				valorTotaPedido = valorTotalItemPedido.add(valorTotaPedido);
//			}
//			
//			pedido.setTaxaFrete(taxaFrete);
//			pedido.setSubtotal(valorTotaPedido);
//			pedido.setValorTotal(valorTotaPedido.add(taxaFrete));
//			
//			pedido.setItens(itensPedido);
//
//			return pedidoRepository.save(pedido);
//		}
//	}
	
	
	private void validarPedido(Pedido pedido) {
	    Cidade cidade = cadastroCidadeService.buscarOuFalhar(pedido.getEnderecoEntrega().getCidade().getId());
	    Usuario cliente = cadastroUsuarioService.buscarOuFalhar(pedido.getCliente().getId());
	    Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(pedido.getRestaurante().getId());
	    FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscarOuFalhar(pedido.getFormaPagamento().getId());

	    pedido.getEnderecoEntrega().setCidade(cidade);
	    pedido.setCliente(cliente);
	    pedido.setRestaurante(restaurante);
	    pedido.setFormaPagamento(formaPagamento);
	    
	    if (restaurante.naoAceitaFormaPagamento(formaPagamento)) {
	        throw new NegocioException(String.format("Forma de pagamento '%s' não é aceita por esse restaurante.",
	                formaPagamento.getDescricao()));
	    }
	}
	
	private void validarItens(Pedido pedido) {
	    pedido.getItens().forEach(item -> {
	        Produto produto = cadastroProdutoService.buscarProdutoRestaurante(
	                pedido.getRestaurante().getId(), item.getProduto().getId());
	        
	        item.setPedido(pedido);
	        item.setProduto(produto);
	        item.setPrecoUnitario(produto.getPreco());
	    });
	}
	
	@Transactional
	public Pedido emitirPedido(Pedido pedido) {
	    validarPedido(pedido);
	    validarItens(pedido);

	    pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
	    pedido.calcularValorTotal();

	    return pedidoRepository.save(pedido);
	}

}
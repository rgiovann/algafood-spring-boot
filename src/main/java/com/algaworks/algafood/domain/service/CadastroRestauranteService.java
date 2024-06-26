package com.algaworks.algafood.domain.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	private final RestauranteRepository restauranteRepository;
	private final CadastroCozinhaService cozinhaService;
	private final CadastroCidadeService  cidadeService;
	private final CadastroFormaPagamentoService  formaPagtoService;
	private final CadastroUsuarioService         usuarioService;
 
	public CadastroRestauranteService(RestauranteRepository restauranteRepository, 
									  CadastroCozinhaService cozinhaService,
									  CadastroCidadeService  cidadeService,
									  CadastroFormaPagamentoService  formaPagtoService,
									  CadastroUsuarioService usuarioService
 									  
									  ) {
		
		this.restauranteRepository = restauranteRepository;
		this.cozinhaService = cozinhaService;
		this.cidadeService = cidadeService;
		this.formaPagtoService = formaPagtoService;
		this.usuarioService = usuarioService;
 
	 		

	}

//	// TESTE
//	public List<RestauranteDto> buscarTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal) {
//		List<Restaurante> restaurante = restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal);
//		return restaurante.stream().map(rest -> modelMapper.map(rest, RestauranteDto.class))
//				.collect(Collectors.toList());
//	}
//
//	// TESTE
//	public List<RestauranteDto> restauranteporNome(String nome, Long cozinhaId) {
//		List<Restaurante> restaurante = restauranteRepository.consultarPorNome(nome, cozinhaId);
//		return restaurante.stream().map(rest -> modelMapper.map(rest, RestauranteDto.class))
//				.collect(Collectors.toList());
//
//	}
//
	// TESTE
	public List<Restaurante> restauranteporNomeCustomizado(String nome, BigDecimal taxaFreteInicial,
			BigDecimal taxaFreteFinal) {
		return  restauranteRepository.findCustomizado(nome, taxaFreteInicial, taxaFreteFinal);


	}
//
//	// TESTE
//	public List<RestauranteDto> restaurantesPorNomeComFreteGratis(String nome) {
// 
//		List<Restaurante> restaurante = restauranteRepository.findComFreteGratis(nome);
//
//		return restaurante.stream().map(rest -> modelMapper.map(rest, RestauranteDto.class))
//				.collect(Collectors.toList());
//
//	}
//
//	// TESTE

//--------------------------------- API OFICIAL-----------------------------------------------------------------------	

//	public Restaurante buscarO(Long restauranteId) {
//	
//		return this.buscarOuFalhar(restauranteId);
//
//	}
	
	@Transactional
	public void ativar(Long restauranteId) {
		
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		
		restaurante.ativar();
		
	}
	
	@Transactional
	public void ativar( List<Long> restauranteIds)
	{
		restauranteIds.forEach(this::ativar);
	}
	
	@Transactional
	public void inativar( List<Long> restauranteIds)
	{
		restauranteIds.forEach(this::inativar);
	}
	
	@Transactional
	public void inativar(Long restauranteId) {
		
		Restaurante restaurante  = buscarOuFalhar(restauranteId);
		
		restaurante.inativar();
		
	}
	
	
	@Transactional
	public void abrir(Long restauranteId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		
		restaurante.abrir();
		
	}

	@Transactional
	public void fechar(Long restauranteId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		
		restaurante.fechar();		
	}
	

	public List<Restaurante> listar() {
		return restauranteRepository.findAll();
	}
		
	
		@Transactional
		public Restaurante salvar(Restaurante restaurante) {
	 
			Cozinha cozinha = cozinhaService.buscarOuFalhar(restaurante.getCozinha().getId());
			Cidade  cidade   = cidadeService.buscarOuFalhar( restaurante.getEndereco().getCidade().getId());
						
			restaurante.setCozinha(cozinha);
			restaurante.getEndereco().setCidade(cidade);
	
			return restauranteRepository.save(restaurante);
		}
	
	
	
	public Restaurante buscarOuFalhar(Long restauranteId) {
		
		return  restauranteRepository.findById(restauranteId)
				.orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId) );
	}
	
	
	public Restaurante restaurantesBuscarPrimeiro() {
		return restauranteRepository.buscarPrimeiro().get();
	}
					
	@Transactional
	public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		
		Restaurante restaurante = this.buscarOuFalhar(restauranteId);
		FormaPagamento formaPagto = formaPagtoService.buscarOuFalhar(formaPagamentoId);
		
		restaurante.removerFormaPagamento(formaPagto);
		
	}
	
	@Transactional
	public void  associarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		
		Restaurante restaurante = this.buscarOuFalhar(restauranteId);
		FormaPagamento formaPagto = formaPagtoService.buscarOuFalhar(formaPagamentoId);
		
		restaurante.adicionarFormaPagamento(formaPagto);
		
	}

	@Transactional
	public void desassociarResponsavel(Long restauranteId, Long usuarioId) {
		Restaurante restaurante = this.buscarOuFalhar(restauranteId);
		Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);
		
		restaurante.removerResponsavel(usuario);
		
	}

	@Transactional
	public void associarResponsavel(Long restauranteId, Long usuarioId) {
		Restaurante restaurante = this.buscarOuFalhar(restauranteId);
		Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);
		
		restaurante.adicionarResponsavel(usuario);		
	}

 

}

package com.algaworks.algafood.domain.service;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.FormaPagamentoNaoEncotradoException;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;

@Service
public class CadastroFormaPagamentoService {
	
	private static final String FORMA_PAGAMENTO_EM_USO = "Forma de pagamento de código %d não pode ser removida, pois está em uso.";
	private final FormaPagamentoRepository formaPagamentoRepository;

	public CadastroFormaPagamentoService(FormaPagamentoRepository formaPagamentoRepository) {
		this.formaPagamentoRepository = formaPagamentoRepository;

	}

	public List<FormaPagamento> listar() {

		return formaPagamentoRepository.findAll();

	}
	
	public OffsetDateTime getDataUltimaAtualizacao() {

		return formaPagamentoRepository.getDataUltimaAtualizacao();

	}

	@Transactional
	public FormaPagamento salvar(FormaPagamento formaPagamento) {

		return formaPagamentoRepository.save(formaPagamento);
	}

	@Transactional
	public void excluir(Long formaPagamentoId) {
		try {

			formaPagamentoRepository.deleteById(formaPagamentoId);
			
			formaPagamentoRepository.flush();

		} catch (EmptyResultDataAccessException e) {
			throw new FormaPagamentoNaoEncotradoException(formaPagamentoId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(FORMA_PAGAMENTO_EM_USO, formaPagamentoId));
		}

	}
	

	public FormaPagamento buscarOuFalhar(Long formaPagamentoId) {
		return formaPagamentoRepository.findById(formaPagamentoId).orElseThrow(() -> new FormaPagamentoNaoEncotradoException(formaPagamentoId));
	}

}
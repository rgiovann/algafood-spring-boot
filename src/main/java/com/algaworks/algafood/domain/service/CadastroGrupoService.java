package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.GrupoNaoEncontradoException;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.GrupoRepository;

@Service
public class CadastroGrupoService {

	private static final String MSG_GRUPO_EM_USO = "Grupo de código %d não pode ser removido, pois está em uso.";
	private final GrupoRepository grupoRepository;
	private final CadastroPermissaoService cadastroPermissaoService;


	public CadastroGrupoService(GrupoRepository grupoRepository,
			                    CadastroPermissaoService cadastroPermissaoService) {
		this.grupoRepository = grupoRepository;
		this.cadastroPermissaoService = cadastroPermissaoService;

	}

 
	public List<Grupo> listar() {

		return grupoRepository.findAll();

	}
	
	@Transactional
	public Grupo salvar(Grupo grupo) {

		return grupoRepository.save(grupo);
	}

	@Transactional
	public void excluir(Long grupoId) {
		try {

			grupoRepository.deleteById(grupoId);
			
			grupoRepository.flush();

		} catch (EmptyResultDataAccessException e) {
			throw new GrupoNaoEncontradoException(grupoId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_GRUPO_EM_USO, grupoId));
		}

	}

	public Grupo buscarOuFalhar(Long grupoId) {
		return grupoRepository.findById(grupoId).orElseThrow(() -> new GrupoNaoEncontradoException(grupoId));
	}
	
	@Transactional
	public void desassociarPermissao(Long grupoId, Long permissaoId) {
		
		Grupo grupo = this.buscarOuFalhar(grupoId);
		Permissao permissao = cadastroPermissaoService.buscarOuFalhar(permissaoId);
		
		grupo.removerPermissao(permissao);
 		
	}
	
	@Transactional
	public void  associarPermissao(Long grupoId, Long permissaoId) {
		
		Grupo grupo = this.buscarOuFalhar(grupoId);
		Permissao permissao = cadastroPermissaoService.buscarOuFalhar(permissaoId);
		grupo.adicionarPermissao(permissao);

		
	}

}
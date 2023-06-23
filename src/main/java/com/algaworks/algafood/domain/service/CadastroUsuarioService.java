package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.Objects;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.SenhaAlteradaIgualAnteriorException;
import com.algaworks.algafood.domain.exception.SenhaAtualNaoConfereException;
import com.algaworks.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;

@Service
public class CadastroUsuarioService {

	private static final String MSG_USUARIO_EM_USO = "Usuário de código %d não pode ser removido, pois está em uso.";
	private static final String MSG_SENHA_ATUAL_NAO_PODE_SER_A_MESMA = "Senha nova do usuário %d igual a senha anterior.";
	private static final String MSG_SENHA_ATUAL_INCORRETA = "Senha atual do usuário %d é incorreta.";

	private final UsuarioRepository usuarioRepository;

	public CadastroUsuarioService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;

	}

	public List<Usuario> listar() {

		return usuarioRepository.findAll();

	}

	@Transactional
	public Usuario salvar(Usuario usuario) {

		return usuarioRepository.save(usuario);
	}

	@Transactional
	public void excluir(Long usuarioId) {
		try {

			usuarioRepository.deleteById(usuarioId);

			usuarioRepository.flush();

		} catch (EmptyResultDataAccessException e) {
			throw new UsuarioNaoEncontradoException(usuarioId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_USUARIO_EM_USO, usuarioId));
		}

	}

	public Usuario buscarOuFalhar(Long usuarioId) {
		return usuarioRepository.findById(usuarioId).orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
	}

	@Transactional
	public void alterarSenha(Usuario usuario, String novaSenha, String senhaAtual) {

		String senhaCadastrada = usuario.getSenha();
		
        if( !(Objects.equals(senhaCadastrada, senhaAtual)) )
		{
			throw new SenhaAtualNaoConfereException(String.format(MSG_SENHA_ATUAL_INCORRETA, usuario.getId()));

		}         
        else
		{
    		if( Objects.equals(senhaCadastrada, novaSenha) ) {
    			
    			throw new SenhaAlteradaIgualAnteriorException(String.format(MSG_SENHA_ATUAL_NAO_PODE_SER_A_MESMA, usuario.getId()));
    			
    		} 
    		else {
			usuario.setSenha(novaSenha);
			usuarioRepository.save(usuario);
    		}
		}
		
	}

}
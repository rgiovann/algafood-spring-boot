package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.UsuarioDtoAssembler;
import com.algaworks.algafood.api.assembler.UsuarioInputDisassembler;
import com.algaworks.algafood.api.dto.UsuarioDto;
import com.algaworks.algafood.api.input.UsuarioComSenhaInput;
import com.algaworks.algafood.api.input.UsuarioInput;
import com.algaworks.algafood.api.input.UsuarioSoSenhaInput;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.SenhaAlteradaIgualAnteriorException;
import com.algaworks.algafood.domain.exception.SenhaAtualNaoConfereException;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {

	private final CadastroUsuarioService usuarioService;
	private final UsuarioDtoAssembler usuarioDtoAssembler;
	private final UsuarioInputDisassembler usuarioInputDisassembler;

	public UsuarioController(CadastroUsuarioService usuarioService, UsuarioDtoAssembler usuarioDtoAssembler,
			UsuarioInputDisassembler usuarioInputDisassembler) {

		this.usuarioService = usuarioService;
		this.usuarioDtoAssembler = usuarioDtoAssembler;
		this.usuarioInputDisassembler = usuarioInputDisassembler;
	}

	@GetMapping
	public List<UsuarioDto> listar() {

		return usuarioDtoAssembler.toCollectionDto(usuarioService.listar());

	}

	@GetMapping("/{usuarioId}")
	public UsuarioDto buscar(@PathVariable Long usuarioId) {

		return usuarioDtoAssembler.toDto(usuarioService.buscarOuFalhar(usuarioId));

	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioDto adicionar(@RequestBody @Valid UsuarioComSenhaInput usuarioNomeInput) {

		return usuarioDtoAssembler.toDto(usuarioService.salvar(usuarioInputDisassembler.toEntity(usuarioNomeInput)));

	}

	@PutMapping("/{usuarioId}")
	public UsuarioDto atualizar(@PathVariable Long usuarioId,
			@RequestBody @Valid UsuarioInput usuarioSemSenhaInput) {
		
		Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);

		usuarioInputDisassembler.copyToEntity(usuarioSemSenhaInput, usuario);

		return usuarioDtoAssembler.toDto(usuarioService.salvar(usuario));

	}

	@DeleteMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long usuarioId) {

		usuarioService.excluir(usuarioId);
	}

	@PutMapping("/{usuarioId}/senha")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void alterarSenha(@PathVariable Long usuarioId,
			@RequestBody @Valid UsuarioSoSenhaInput usuarioSoSenhaInput) {

		Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);
		
		try {
		
			usuarioService.alterarSenha(usuario,usuarioSoSenhaInput.getNovaSenha(),usuarioSoSenhaInput.getSenhaAtual());
		
		} catch (SenhaAlteradaIgualAnteriorException e) {

			throw new NegocioException(e.getMessage(), e);
		}
		catch (SenhaAtualNaoConfereException e) {

			throw new NegocioException(e.getMessage(), e);
		}
	}
}

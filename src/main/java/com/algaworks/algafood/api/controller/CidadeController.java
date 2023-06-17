package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
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

import com.algaworks.algafood.api.dto.CidadeDto;
import com.algaworks.algafood.api.input.CidadeInput;
import com.algaworks.algafood.api.input.CozinhaIdInput;
import com.algaworks.algafood.api.input.EstadoIdInput;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {

	private final CadastroCidadeService cidadeService;
	private final ModelMapper modelMapper;

	public CidadeController(CadastroCidadeService cidadeService, ModelMapper modelMapper) {
		this.cidadeService = cidadeService;
		this.modelMapper = modelMapper;
	}

	@GetMapping
	public List<CidadeDto> listar() {

		return cidadeService.listar().stream().map(cid -> modelMapper.map(cid, CidadeDto.class))
				.collect(Collectors.toList());

	}

	@GetMapping("/{cidadeId}")
	public CidadeDto buscar(@PathVariable Long cidadeId) {

		return modelMapper.map(cidadeService.buscarOuFalhar(cidadeId), CidadeDto.class);

	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeDto adicionar(@RequestBody @Valid CidadeInput cidadeInput) {

		try {

			Cidade cidade = modelMapper.map(cidadeInput, Cidade.class);

			return modelMapper.map(cidadeService.salvar(cidade), CidadeDto.class);

		} catch (EstadoNaoEncontradoException e) {

			throw new NegocioException(e.getMessage(), e);
		}

	}

	@PutMapping("/{cidadeId}")
	public CidadeDto atualizar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeInput cidadeInput) {

		Cidade cidade = cidadeService.buscarOuFalhar(cidadeId);

		// Para evitar org.hibernate.HibernateException: identifier of an instance of
		// com.algaworks.algafood.domain.model.Cozinha was altered from 1 to 2
		//cidade.setEstado(new Estado());
		
	    // Define o conversor
        Converter<EstadoIdInput, Estado> estadoConverter = new Converter<EstadoIdInput, Estado>() {
            @Override
            public Estado convert(MappingContext<EstadoIdInput, Estado> context) {
            	Estado estado = new Estado(); 
            	estado.setId(context.getSource().getId());
                return estado;
            }
        };
        // adicina o conversor ao bean modelMapper
        modelMapper.addConverter(estadoConverter, EstadoIdInput.class, Estado.class);
        
 
		modelMapper.map(cidadeInput, cidade);
		
		try {
			return modelMapper.map(cidadeService.salvar(cidade), CidadeDto.class);

		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}

	}

	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cidadeId) {

		cidadeService.excluir(cidadeId);
		return;
	}

}

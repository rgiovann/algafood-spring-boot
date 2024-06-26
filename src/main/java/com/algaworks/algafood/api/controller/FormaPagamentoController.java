package com.algaworks.algafood.api.controller;

import java.time.OffsetDateTime;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import com.algaworks.algafood.api.assembler.FormaPagamentoDtoAssembler;
import com.algaworks.algafood.api.assembler.FormaPagamentoInputDisassembler;
import com.algaworks.algafood.api.dto.FormaPagamentoDto;
import com.algaworks.algafood.api.input.FormaPagamentoNomeInput;
import com.algaworks.algafood.api.openapi.controller.FormaPagamentoControllerOpenApi;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;

@RestController
@RequestMapping(value = "/formaspagamento")
public class FormaPagamentoController implements FormaPagamentoControllerOpenApi {

	private final CadastroFormaPagamentoService formaPagamentoService;
	private final FormaPagamentoDtoAssembler formaPagtoDtoAssembler;
	private final FormaPagamentoInputDisassembler formaPagtoInputDisassembler;

	  public FormaPagamentoController(CadastroFormaPagamentoService formaPagamentoService,
			  						  FormaPagamentoDtoAssembler formaPagamentoDtoAssembler,
			  						  FormaPagamentoInputDisassembler formaPagtoDtoDisassembler) {
	    this.formaPagamentoService = formaPagamentoService;
	    this.formaPagtoDtoAssembler = formaPagamentoDtoAssembler;
		this.formaPagtoInputDisassembler = formaPagtoDtoDisassembler;

	  }
	
//		// TESTE
//		@GetMapping("/por-nome")
//		public List<CozinhaDto> listarporNome(@RequestParam("nomeCozinha") String nome) {
//
//			return formaPagamentoService.buscarPorNome(nome)
//					.stream()
//					.map(cozinha-> modelMapper.map(cozinha, CozinhaDto.class))
//					.collect(Collectors.toList());
//
//		}	  
		
//---------------------------------------- API OFICIAL -------------------------------------------------
//------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------
	@Override
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CollectionModel<FormaPagamentoDto>> listar(ServletWebRequest request) {
		
		// desabilita o Spring de gerar o eTag
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
		
		String eTag = "0";
		
		OffsetDateTime dataUltimaAtualizacao = formaPagamentoService.getDataUltimaAtualizacao();
		
		if( dataUltimaAtualizacao != null) {
			eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
		}
		
		// já temos condição de saber se continuamos ou não o proecessamento;
		
		if(request.checkNotModified(eTag)) {
			return null;
		}

		CollectionModel<FormaPagamentoDto> formasPagamentoDto = formaPagtoDtoAssembler
				                                     .toCollectionModel(formaPagamentoService.listar());
		
		return ResponseEntity.ok()
				//.cacheControl(CacheControl.maxAge(20,TimeUnit.SECONDS))
				//.cacheControl(CacheControl.maxAge(20,TimeUnit.SECONDS).cachePrivate())
				.cacheControl(CacheControl.maxAge(20,TimeUnit.SECONDS).cachePublic())  //default
				.eTag(eTag)
				//.cacheControl(CacheControl.noCache()) // ao fazer cache sempre validar (sempre está stale)
				//.cacheControl(CacheControl.noStore())  // desativa cache completamente
				.body(formasPagamentoDto);

	}
	
	
	@Override	
	@GetMapping(path="/{fomaPagamentoId}",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<FormaPagamentoDto> buscar(@PathVariable Long fomaPagamentoId,ServletWebRequest request) {

		// desabilita o Spring de gerar o eTag
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
		
		String eTag = "0";
		
		OffsetDateTime dataUltimaAtualizacao = formaPagamentoService.getDataUltimaAtualizacao();
		
		if( dataUltimaAtualizacao != null) {
			eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
		}
				
		if(request.checkNotModified(eTag)) {
			return null;
		}
		
		FormaPagamentoDto formaPagamentoDto = formaPagtoDtoAssembler.toModel(formaPagamentoService.buscarOuFalhar(fomaPagamentoId));
		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(20,TimeUnit.SECONDS).cachePublic())
				.eTag(eTag)
				.body(formaPagamentoDto);

	}

	@Override
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public FormaPagamentoDto adicionar(@RequestBody @Valid FormaPagamentoNomeInput formaPagamentoInput) {
		
		FormaPagamento formaPagamento  = formaPagamentoService
									     .salvar( formaPagtoInputDisassembler.toEntity(formaPagamentoInput)); 
 		
		return formaPagtoDtoAssembler.toModel(formaPagamento);

	}
	@Override	
	@PutMapping(path="/{formaPagamentoId}",produces = MediaType.APPLICATION_JSON_VALUE)
	public FormaPagamentoDto atualizar(@PathVariable Long formaPagamentoId, @RequestBody @Valid  FormaPagamentoNomeInput formaPagamentoInput)
	{        
		FormaPagamento formaPagamento  = formaPagamentoService.buscarOuFalhar(formaPagamentoId);
			
		formaPagtoInputDisassembler.copyToEntity(formaPagamentoInput, formaPagamento);
			
		return formaPagtoDtoAssembler.toModel(formaPagamentoService.salvar(formaPagamento));
 
	}
	@Override
	@DeleteMapping("/{fomaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long fomaPagamentoId) {

			formaPagamentoService.excluir(fomaPagamentoId);
	}


}

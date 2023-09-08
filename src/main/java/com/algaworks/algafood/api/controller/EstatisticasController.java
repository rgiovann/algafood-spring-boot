package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.dto.VendaDiaria;
import com.algaworks.algafood.api.openapi.controller.EstatisticasControllerOpenApi;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.service.VendaQueryService;
import com.algaworks.algafood.domain.service.VendaReportService;

@RestController
@RequestMapping(path ="/estatisticas")
public class EstatisticasController implements EstatisticasControllerOpenApi {
	
	private VendaQueryService vendaQueryService;
	
	private VendaReportService vendaReportService;


	public EstatisticasController(VendaQueryService vendasQueryService,
								  VendaReportService vendaReportService) {
 		this.vendaQueryService = vendasQueryService;
 		this.vendaReportService = vendaReportService;

	}
	
//     **************************************
//        versao paged de vendas diarias.
//     **************************************	//
//	@GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
//	public Page<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter vendaDiariaFilter,
//			                               @PageableDefault(size=10)  Pageable pageable, 
//			                               @RequestParam(required=false, defaultValue="+00:00") String timeOffset){
//		
//		return vendasQueryService.consultarVendasDiarias(vendaDiariaFilter, pageable,timeOffset);
// 		
//	}
// ***************************************************************************************************************	
	
	@Override
	@GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro,
			@RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
		return vendaQueryService.consultarVendasDiarias(filtro, timeOffset);
	}
	
	@Override
	@GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> consultarVendasDiariasPdf(VendaDiariaFilter vendaDiariaFilter,
			                               @PageableDefault(size=10)  Pageable pageable, 
			                               @RequestParam(required=false, defaultValue="+00:00") String timeOffset){
		
		byte[] bytesPdf = vendaReportService.emitirVendasDiarias(vendaDiariaFilter, timeOffset);
		
		var headers = new HttpHeaders();
		
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf");
		
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.headers(headers)
				.body(bytesPdf);
 		
	}

 

}
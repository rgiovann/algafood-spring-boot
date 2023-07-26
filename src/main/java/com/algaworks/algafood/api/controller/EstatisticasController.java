package com.algaworks.algafood.api.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.dto.VendaDiaria;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.service.VendaQueryService;

@RestController
@RequestMapping(path ="/estatisticas")
public class EstatisticasController {
	
	private VendaQueryService vendasQueryService;

	public EstatisticasController(VendaQueryService vendasQueryService) {
 		this.vendasQueryService = vendasQueryService;
	}
	
	@GetMapping("/vendas-diarias")
	public Page<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter vendaDiariaFilter,@PageableDefault(size=10)  Pageable pageable){
		
		return vendasQueryService.consultarVendasDiarias(vendaDiariaFilter, pageable);
 		
	}
	

}
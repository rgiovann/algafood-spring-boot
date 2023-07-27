package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algaworks.algafood.api.dto.VendaDiaria;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;

public interface VendaQueryService {
	
	Page<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, Pageable pageable, String timeOffset);
	
	List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro,String timeOffset);



}

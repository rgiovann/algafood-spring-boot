package com.algaworks.algafood.infraestructure.service.report;

import java.util.HashMap;
import java.util.Locale;

import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.service.VendaQueryService;
import com.algaworks.algafood.domain.service.VendaReportService;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class PdfVendaReportService implements VendaReportService {

	private final VendaQueryService vendaQueryService;

	public PdfVendaReportService(VendaQueryService vendaQueryService) {
		this.vendaQueryService = vendaQueryService;
	}

	@SuppressWarnings("deprecation")
	@Override
	public byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset) {
		try {
			var inputStream = this.getClass().getResourceAsStream("/relatorios/vendas-diarias.jasper");

			var parameters = new HashMap<String, Object>();
			parameters.put("REPORT_LOCALE", new Locale("pt", "BR"));

			var vendasDiarias = vendaQueryService.consultarVendasDiarias(filtro, timeOffset);

			var dataSource = new JRBeanCollectionDataSource(vendasDiarias);

			var jasperPrint = JasperFillManager.fillReport(inputStream, parameters, dataSource);
			return JasperExportManager.exportReportToPdf(jasperPrint);
		} catch (Exception e) {
			throw new ReportException("Não foi possível emitir relatório de vendas diárias", e);
		}
	}

}

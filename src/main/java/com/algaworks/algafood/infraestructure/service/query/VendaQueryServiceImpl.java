package com.algaworks.algafood.infraestructure.service.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.api.dto.VendaDiaria;
import com.algaworks.algafood.domain.enumeration.StatusPedido;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.VendaQueryService;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService{
	
//               ********************************
//                paged version of vendas diarias
//               ********************************
//	@PersistenceContext
//	private EntityManager manager;
//	
//	private String timeOffset;
//
//	@Override
//	public Page<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, Pageable pageable, String timeOffset) {
//		var builder = manager.getCriteriaBuilder();
//		var query   = builder.createQuery(VendaDiaria.class);
//		var root    = query.from(Pedido.class);			
//		this.timeOffset = timeOffset;
//			
//		var functioDateDataCriacao = getFunctionDate(builder,root);
//				
//		var selection = builder.construct(VendaDiaria.class, 
//				functioDateDataCriacao,
//				builder.count(root.get("id")),
//				builder.sum(root.get("valorTotal")));
//		
//				
//		query.select(selection);
//		query.where( createPredicates(root, builder, filtro));		
//		query.groupBy(functioDateDataCriacao);
//
//        List<VendaDiaria> resultList = manager.createQuery(query)
//                .setFirstResult(Integer.valueOf(String.valueOf(pageable.getOffset())))
//                .setMaxResults(pageable.getPageSize())
//                .getResultList();
//
//        return new PageImpl<VendaDiaria>(resultList, pageable, totalRecords(filtro));
//		
//		//return manager.createQuery(query).getResultList();
//	}
//	
//	private Predicate[] createPredicates(Root<Pedido> root, 
//			                       CriteriaBuilder builder, 
//			                      VendaDiariaFilter filtro) {
//		
//		var functioDateDataCriacao = getFunctionDate(builder,root);
//
//		var predicates = new ArrayList<Predicate>();
//		
//		if (filtro.getRestauranteId() != null) {
//			predicates.add(builder.equal(root.get("restaurante"), filtro.getRestauranteId()));
//		}
//
//		if (filtro.getDataCriacaoInicio() != null) {
//			predicates.add(builder.greaterThanOrEqualTo(functioDateDataCriacao, Date.from(filtro.getDataCriacaoInicio().toInstant()))) ;
//		}
//		
//
//		if (filtro.getDataCriacaoFim() != null) {
//			predicates.add(builder.lessThanOrEqualTo(functioDateDataCriacao, Date.from(filtro.getDataCriacaoFim().toInstant()))) ;
//		}
//		
//		predicates.add(root.get("status").in(
//		        StatusPedido.CONFIRMADO, StatusPedido.ENTREGUE));
//        return predicates.toArray(new Predicate[predicates.size()]);
//
//	}
//	
//	
//	// get the total number of records filtered
//    private Long totalRecords(VendaDiariaFilter filtro) {
//        var builder = manager.getCriteriaBuilder();
//        var criteria = builder.createQuery(Long.class);
//        var root = criteria.from(Pedido.class);
//
//        var predicates = createPredicates(root, builder, filtro);
//        criteria.where(predicates);
//
//        criteria.select(builder.count(root));
//
//        return manager.createQuery(criteria).getSingleResult();
//    }
//	
//	
//	private Expression<Date> getFunctionDate(CriteriaBuilder builder, Root<Pedido> root)
//			{
//		
//		Expression<Date> convertTzDataCriacao = builder.function("convert_tz", 
//				Date.class,
//				root.get("dataCriacao"),
//                builder.literal("+00:00"),builder.literal(this.timeOffset));	
//		
//		return builder.function("date", 
//                Date.class,
//                convertTzDataCriacao);	
//	}
// *********************************************************************************************
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffset) {
		var builder = manager.getCriteriaBuilder();
		var query = builder.createQuery(VendaDiaria.class);
		var root = query.from(Pedido.class);
		var predicates = new ArrayList<Predicate>();
		
		var functionConvertTzDataCriacao = builder.function(
				"convert_tz", Date.class, root.get("dataCriacao"),
				builder.literal("+00:00"), builder.literal(timeOffset));
		
		var functionDateDataCriacao = builder.function(
				"date", Date.class, functionConvertTzDataCriacao);
		
		var selection = builder.construct(VendaDiaria.class,
				functionDateDataCriacao,
				builder.count(root.get("id")),
				builder.sum(root.get("valorTotal")));
		
		if (filtro.getRestauranteId() != null) {
			predicates.add(builder.equal(root.get("restaurante"), filtro.getRestauranteId()));
		}
	      
		if (filtro.getDataCriacaoInicio() != null) {
			predicates.add(builder.greaterThanOrEqualTo(functionDateDataCriacao, Date.from(filtro.getDataCriacaoInicio().toInstant())));
		}

		if (filtro.getDataCriacaoFim() != null) {
			predicates.add(builder.lessThanOrEqualTo(functionDateDataCriacao, Date.from(filtro.getDataCriacaoFim().toInstant())));
		}
	      
		predicates.add(root.get("status").in(
				StatusPedido.CONFIRMADO, StatusPedido.ENTREGUE));
		
		query.select(selection);
		query.where(predicates.toArray(new Predicate[0]));
		query.groupBy(functionDateDataCriacao);
		
		return manager.createQuery(query).getResultList();
	}

	@Override
	public Page<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, Pageable pageable, String timeOffset) {
		// TODO Auto-generated method stub
		return null;
	}
	
}

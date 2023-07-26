package com.algaworks.algafood.infraestructure.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.api.dto.VendaDiaria;
import com.algaworks.algafood.domain.enumeration.StatusPedido;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.VendaQueryService;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService{
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, Pageable pageable) {
		var builder = manager.getCriteriaBuilder();
		var query   = builder.createQuery(VendaDiaria.class);
		var root    = query.from(Pedido.class);			
			
		var functioDateDataCriacao = getFunctionDate(builder,root);
				
		var selection = builder.construct(VendaDiaria.class, 
				functioDateDataCriacao,
				builder.count(root.get("id")),
				builder.sum(root.get("valorTotal")));
		
				
		query.select(selection);
		query.where( createPredicates(root, builder, filtro));		
		query.groupBy(functioDateDataCriacao);

        List<VendaDiaria> resultList = manager.createQuery(query)
                .setFirstResult(Integer.valueOf(String.valueOf(pageable.getOffset())))
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return new PageImpl<VendaDiaria>(resultList, pageable, totalRecords(filtro));
		
		//return manager.createQuery(query).getResultList();
	}
	
	private Predicate[] createPredicates(Root<Pedido> root, CriteriaBuilder builder, VendaDiariaFilter filtro) {
		
		var functioDateDataCriacao = getFunctionDate(builder,root);

		var predicates = new ArrayList<Predicate>();
		
		if (filtro.getRestauranteId() != null) {
			predicates.add(builder.equal(root.get("restaurante"), filtro.getRestauranteId()));
		}

		if (filtro.getDataCriacaoInicio() != null) {
			predicates.add(builder.greaterThanOrEqualTo(functioDateDataCriacao, Date.from(filtro.getDataCriacaoInicio().toInstant()))) ;
		}
		

		if (filtro.getDataCriacaoFim() != null) {
			predicates.add(builder.lessThanOrEqualTo(functioDateDataCriacao, Date.from(filtro.getDataCriacaoFim().toInstant()))) ;
		}
		
		predicates.add(root.get("status").in(
		        StatusPedido.CONFIRMADO, StatusPedido.ENTREGUE));
        return predicates.toArray(new Predicate[predicates.size()]);

	}
	
	
	// get the total number of records filtered
    private Long totalRecords(VendaDiariaFilter filtro) {
        var builder = manager.getCriteriaBuilder();
        var criteria = builder.createQuery(Long.class);
        var root = criteria.from(Pedido.class);

        var predicates = createPredicates(root, builder, filtro);
        criteria.where(predicates);

        criteria.select(builder.count(root));

        return manager.createQuery(criteria).getSingleResult();
    }
	
	
	private Expression<Date> getFunctionDate(CriteriaBuilder builder, Root<Pedido> root)
			{
		
		return builder.function("date", 
                Date.class,
                root.get("dataCriacao"));	
	}

}

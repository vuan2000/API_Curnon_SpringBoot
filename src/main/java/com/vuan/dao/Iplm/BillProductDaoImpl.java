package com.vuan.dao.Iplm;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vuan.dao.BillProductDao;
import com.vuan.entity.BillProduct;
import com.vuan.model.SearchBillProductDTO;

@Transactional
@Repository
public class BillProductDaoImpl extends JPARepository<BillProduct> implements BillProductDao {
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public BillProduct get(int id) {
		return entityManager.find(BillProduct.class, id);
	}

	@Override
	public List<BillProduct> getAll(SearchBillProductDTO searchBillProductDTO) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<BillProduct> criteriaQuery = criteriaBuilder.createQuery(BillProduct.class);
		Root<BillProduct> root = criteriaQuery.from(BillProduct.class);
		CriteriaQuery<BillProduct> all = criteriaQuery.select(root);
		
		TypedQuery<BillProduct> typedQuery = entityManager.createQuery(all);
		return typedQuery.getResultList();
	}

	@Override
	public long countShowAll(SearchBillProductDTO searchBillProductDTO) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<BillProduct> root = criteriaQuery.from(BillProduct.class);
		
		TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery.select(criteriaBuilder.count(root)));
		return typedQuery.getSingleResult();
	}

	@Override
	public List<BillProduct> searchByIdBill(int id) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<BillProduct> criteriaQuery = criteriaBuilder.createQuery(BillProduct.class);
		Root<BillProduct> root = criteriaQuery.from(BillProduct.class);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if(StringUtils.isNotBlank(String.valueOf(id))) {
			Predicate predicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("id_bill")), 
					"%"+ String.valueOf(id).toLowerCase() +"%");
			
			predicates.add(predicate);
		}
		
		criteriaQuery.where(predicates.toArray(new Predicate[] {}));
		
		TypedQuery<BillProduct> query = entityManager.createQuery(criteriaQuery.select(root).distinct(true));
		return query.getResultList();
	}

	@Override
	public long countSearcgByIdBill(int id) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<BillProduct> root = criteriaQuery.from(BillProduct.class);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if(StringUtils.isNotBlank(String.valueOf(id))) {
			Predicate predicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("id_bill")), 
					"%"+ String.valueOf(id).toLowerCase() +"%");
			
			predicates.add(predicate);
		}
		
		criteriaQuery.where(predicates.toArray(new Predicate[] {}));
		
		TypedQuery<Long> query = entityManager.createQuery(criteriaQuery.select(criteriaBuilder.count(root)));
		return query.getSingleResult();
	}


}

package com.vuan.dao.Iplm;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vuan.dao.ProductDao;
import com.vuan.entity.Product;
import com.vuan.model.SearchProductDTO;

@Repository //lam viec vs csdl
@Transactional
public class ProductDaoIplm extends JPARepository<Product> implements ProductDao{
	@Autowired
	EntityManager entityManager;

	@Override
	public Product get(int id) {
		return entityManager.find(Product.class, id);
	}

	@Override
	public List<Product> search(SearchProductDTO searchProductDTO) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if(StringUtils.isNotBlank(searchProductDTO.getKeyword())) {
			Predicate predicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
					"%" + searchProductDTO.getKeyword() + "%");
			predicates.add(predicate);
			
		}
//		if(searchProductDTO.getFormPrice() !=  searchProductDTO.getToPrice()) {
//			Predicate predicate1 = (Predicate) Restrictions.gt("price", searchProductDTO.getFormPrice() );
//			Predicate predicate2 = (Predicate) Restrictions.lt("price", searchProductDTO.getToPrice() );
//			
//			predicates.add(criteriaBuilder.and(predicate1 ,predicate2));
//		}
		criteriaQuery.where(predicates.toArray(new Predicate[] {}));
		
		TypedQuery<Product> typedQuery = entityManager.createQuery(criteriaQuery.select(root).distinct(true));
		if(searchProductDTO.getStart() != null) {
			typedQuery.setFirstResult(searchProductDTO.getStart());
			typedQuery.setMaxResults(searchProductDTO.getLength());
		}
		return typedQuery.getResultList();
	}

	@Override
	public Long countSearch(SearchProductDTO searchProductDTO) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if(StringUtils.isNotBlank(searchProductDTO.getKeyword())) {
			Predicate predicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
					"%" + searchProductDTO.getKeyword() + "%");
			predicates.add(predicate);
			
		}
//		if(searchProductDTO.getFormPrice() !=  searchProductDTO.getToPrice()) {
//			Predicate predicate1 = (Predicate) Restrictions.gt("price", searchProductDTO.getFormPrice() );
//			Predicate predicate2 = (Predicate) Restrictions.lt("price", searchProductDTO.getToPrice() );
//			
//			predicates.add(criteriaBuilder.and(predicate1 ,predicate2));
//		}
		criteriaQuery.where(predicates.toArray(new Predicate[] {}));
		
		TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery.select(criteriaBuilder.count(root)));
		return typedQuery.getSingleResult();
	}

	@Override
	public Long countGetAll(SearchProductDTO searchProductDTO) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		
		TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery.select(criteriaBuilder.count(root)));
		return typedQuery.getSingleResult();
	}

}

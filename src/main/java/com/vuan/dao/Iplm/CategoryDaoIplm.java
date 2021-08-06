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

import com.vuan.dao.CategoryDao;
import com.vuan.entity.Category;
import com.vuan.model.SearchCategoryDTO;

@Repository //lam viec voi csdl
@Transactional // quan ly giao dich.
//dam bao tat ca cac ham deu thanh cong hoac faile
public class CategoryDaoIplm extends JPARepository<Category> implements CategoryDao{
	@Autowired
	private EntityManager entityManager;
	
	public Category get(int id) {
		return entityManager.find(Category.class, id);
	}

	@Override
	public List<Category> search(SearchCategoryDTO searchCategoryDTO) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Category> criteriaQuery = criteriaBuilder.createQuery(Category.class);
		Root<Category> root = criteriaQuery.from(Category.class);

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (StringUtils.isNotBlank(searchCategoryDTO.getKeyword())) {
			Predicate predicate = criteriaBuilder.like( criteriaBuilder.lower(root.get("name")),
					"%" + searchCategoryDTO.getKeyword().toLowerCase() + "%" );
			predicates.add(predicate);
		}

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		// order
//		if (StringUtils.equals(searchCategoryDTO.getSortBy().getData(), "id")) {
//			System.out.println("có sort");
//			if (searchCategoryDTO.getSortBy().isAsc()) {
//				criteriaQuery.orderBy(criteriaBuilder.asc(root.get("id")));
//			} else {
//				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
//			}
//		} else if (StringUtils.equals(searchCategoryDTO.getSortBy().getData(), "name")) {
//			System.out.println("có sort");
//			if (searchCategoryDTO.getSortBy().isAsc()) {
//				criteriaQuery.orderBy(criteriaBuilder.asc(root.get("name")));
//			} else {
//				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("name")));
//			}
//		}

		TypedQuery<Category> typedQuery = entityManager.createQuery(criteriaQuery.select(root));
		if (searchCategoryDTO.getStart() != null) {
			System.out.println("truyen start length");
			typedQuery.setFirstResult((searchCategoryDTO.getStart()));
			typedQuery.setMaxResults(searchCategoryDTO.getLength());
		}
		return typedQuery.getResultList();
	}

	@Override
	public Long countSearch(SearchCategoryDTO searchCategoryDTO) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<Category> root = criteriaQuery.from(Category.class);

		// Constructing list of parameters
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (StringUtils.isNotBlank(searchCategoryDTO.getKeyword())) {
			Predicate predicate = builder.like(builder.lower(root.get("name")),
					"%" + searchCategoryDTO.getKeyword().toLowerCase() + "%");
			predicates.add(predicate);
		}

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));
		TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery.select(builder.count(root)));
		return typedQuery.getSingleResult();
	}

	@Override
	public Long countTotal(SearchCategoryDTO searchCategoryDTO) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<Category> root = criteriaQuery.from(Category.class);

		TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery.select(builder.count(root)));
		return typedQuery.getSingleResult();
	}
}

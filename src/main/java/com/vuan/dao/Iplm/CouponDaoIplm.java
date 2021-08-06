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

import com.vuan.dao.CouponDao;
import com.vuan.entity.Coupon;
import com.vuan.model.SearchCouponDTO;

@Repository
@Transactional
public class CouponDaoIplm extends JPARepository<Coupon> implements CouponDao{
	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public Coupon get(int id) {
		return entityManager.find(Coupon.class, id);
	}

	@Override
	public Coupon getByCode(String code) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Coupon> criteriaQuery = criteriaBuilder.createQuery(Coupon.class);
		Root<Coupon> root = criteriaQuery.from(Coupon.class);
		
		List<Predicate> predicates =new ArrayList<Predicate>();
		if(StringUtils.isNotBlank(code)) {
			Predicate predicate = criteriaBuilder.like( criteriaBuilder.lower(root.get("code")),
					"%" + code + "%" );
			predicates.add(predicate);
		}
		
		criteriaQuery.where(predicates.toArray(new Predicate[] {}));
		
		TypedQuery<Coupon> typedQuery = entityManager.createQuery(criteriaQuery.select(root));
		return typedQuery.getSingleResult(); 
	}

	@Override
	public List<Coupon> showAll(SearchCouponDTO searchCouponDTO) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Coupon> criteriaQuery = criteriaBuilder.createQuery(Coupon.class);
		Root<Coupon> root = criteriaQuery.from(Coupon.class);
	
		
		TypedQuery<Coupon> typedQuery = entityManager.createQuery(criteriaQuery.select(root));
		if (searchCouponDTO.getStart() != null) {
			System.out.println("truyen start length");
			typedQuery.setFirstResult((searchCouponDTO.getStart()));
			typedQuery.setMaxResults(searchCouponDTO.getLength());
		}
		return typedQuery.getResultList();
	}

	@Override
	public long countShowAll(SearchCouponDTO searchCouponDTO) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<Coupon> root = criteriaQuery.from(Coupon.class);
	
		
		TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery.select(criteriaBuilder.count(root)));
		if (searchCouponDTO.getStart() != null) {
			System.out.println("truyen start length");
			typedQuery.setFirstResult((searchCouponDTO.getStart()));
			typedQuery.setMaxResults(searchCouponDTO.getLength());
		}
		return typedQuery.getSingleResult();
	}

}

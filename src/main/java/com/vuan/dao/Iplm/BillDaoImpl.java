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

import com.vuan.dao.BillDao;
import com.vuan.entity.Bill;
import com.vuan.model.SearchBillDTO;

@Transactional
@Repository
public class BillDaoImpl extends JPARepository<Bill> implements BillDao {
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public Bill get(int id) {
		return entityManager.find(Bill.class, id);
	}

	@Override
	public List<Bill> searchByNameBuyer(SearchBillDTO searchBillDTO) {
//		String jql = "select b from Bill b join b.buyer u where u.name like :uname";
//		return entityManager.createQuery(jql, Bill.class).
//				setParameter("uname", "%" + nameBuyer + "%").setFirstResult(start).
//				setMaxResults(length).getResultList();
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Bill> criteriaQuery = criteriaBuilder.createQuery(Bill.class);
		Root<Bill> root = criteriaQuery.from(Bill.class);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if(StringUtils.isNotBlank(searchBillDTO.getKeyword())) {
			Predicate predicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
					"%" + searchBillDTO.getKeyword() + "%");
			predicates.add(predicate);
			
		}
		criteriaQuery.where(predicates.toArray(new Predicate[] {}));
		
		TypedQuery<Bill> typedQuery = entityManager.createQuery(criteriaQuery.select(root).distinct(true));
		if(searchBillDTO.getStart() != null) {
			typedQuery.setFirstResult(searchBillDTO.getStart());
			typedQuery.setMaxResults(searchBillDTO.getLength());
		}
		return typedQuery.getResultList();
	}

	@Override
	public List<Bill> showAllBill(SearchBillDTO searchBillDTO) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Bill> criteriaQuery = criteriaBuilder.createQuery(Bill.class);
		Root<Bill> root = criteriaQuery.from(Bill.class);
		
		TypedQuery<Bill> typedQuery = entityManager.createQuery(criteriaQuery.select(root));
		return typedQuery.getResultList();
	}

	@Override
	public long countSearchByNameBuyer(SearchBillDTO searchBillDTO) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<Bill> root = criteriaQuery.from(Bill.class);
		
		TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery.select(criteriaBuilder.count(root)));
		return typedQuery.getSingleResult();
	}

	@Override
	public long countShowAllBill(SearchBillDTO searchBillDTO) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<Bill> root = criteriaQuery.from(Bill.class);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if(StringUtils.isNotBlank(searchBillDTO.getKeyword())) {
			Predicate predicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
					"%" + searchBillDTO.getKeyword() + "%");
			predicates.add(predicate);
			
		}
		criteriaQuery.where(predicates.toArray(new Predicate[] {}));
		
		TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery.select(criteriaBuilder.count(root)));
		if(searchBillDTO.getStart() != null) {
			typedQuery.setFirstResult(searchBillDTO.getStart());
			typedQuery.setMaxResults(searchBillDTO.getLength());
		}
		return typedQuery.getSingleResult();
	}

}

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

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vuan.dao.UserDao;
import com.vuan.entity.User;
import com.vuan.model.SearchUserDTO;

@Repository // lam viec voi csdl
@Transactional // quan ly giao dich.
//dam bao tat ca cac ham deu thanh cong hoac faile
public class UserDaoIplm extends JPARepository<User> implements UserDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public User get(int id) {

		return entityManager.find(User.class, id);
	}

	@Override
	public User getByUserName(String username) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
		Root<User> root = criteriaQuery.from(User.class);

		criteriaQuery.where(criteriaBuilder.like(criteriaBuilder.lower(root.get("username")), username.toLowerCase()));

		TypedQuery<User> typedQuery = entityManager.createQuery(criteriaQuery.select(root).distinct(true));
		return typedQuery.getSingleResult();
	}
	
	@Override
	public Boolean exitsUsername(String username) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
		Root<User> root = criteriaQuery.from(User.class);

		criteriaQuery.select(root).where(criteriaBuilder.equal(criteriaBuilder.lower(root.get("username")), username.toLowerCase()));

		TypedQuery<User> typedQuery = entityManager.createQuery(criteriaQuery.select(root));
		try {
			return typedQuery.getSingleResult() != null  ? true : false;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<User> searchUser(SearchUserDTO searchUserDTO) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
		Root<User> root = criteriaQuery.from(User.class);

		List<Predicate> predicates = new ArrayList<Predicate>();

		Predicate predicate1 = criteriaBuilder.like(root.get("role"), "%" + "ROLE_MEMBER" + "%");
		Predicate predicate2 = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
				"%" + searchUserDTO.getKeyword().toLowerCase() + "%");
		predicates.add(criteriaBuilder.and(predicate1, predicate2));

		TypedQuery<User> query = entityManager
				.createQuery(criteriaQuery.select(root).where(predicates.toArray(new Predicate[] {})));
		if (searchUserDTO.getStart() != null) {
			query.setFirstResult(searchUserDTO.getStart());
			query.setMaxResults(searchUserDTO.getLength());
		}
		return query.getResultList();
	}

	@Override
	public List<User> searchAdmin(SearchUserDTO searchUserDTO) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
		Root<User> root = criteriaQuery.from(User.class);

		List<Predicate> predicates = new ArrayList<Predicate>();

		Predicate predicate1 = criteriaBuilder.like(root.get("role"), "%" + "ROLE_ADMIN" + "%");
		Predicate predicate2 = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
				"%" + searchUserDTO.getKeyword().toLowerCase() + "%");
		predicates.add(criteriaBuilder.and(predicate1, predicate2));

		TypedQuery<User> query = entityManager
				.createQuery(criteriaQuery.select(root).where(predicates.toArray(new Predicate[] {})));
		if (searchUserDTO.getStart() != null) {
			query.setFirstResult(searchUserDTO.getStart());
			query.setMaxResults(searchUserDTO.getLength());
		}
		return query.getResultList();
	}

	@Override
	public Long countSearchUser(SearchUserDTO searchUserDTO) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<User> root = criteriaQuery.from(User.class);

		List<Predicate> predicates = new ArrayList<Predicate>();

		Predicate predicate1 = criteriaBuilder.like(root.get("role"), "%" + "ROLE_MEMBER" + "%");
		Predicate predicate2 = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
				"%" + searchUserDTO.getKeyword().toLowerCase() + "%");
		predicates.add(criteriaBuilder.and(predicate1, predicate2));

		TypedQuery<Long> query = entityManager.createQuery(
				criteriaQuery.select(criteriaBuilder.count(root)).where(predicates.toArray(new Predicate[] {})));
		return query.getSingleResult();
	}

	@Override
	public Long countSearchAdmin(SearchUserDTO searchUserDTO) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<User> root = criteriaQuery.from(User.class);

		List<Predicate> predicates = new ArrayList<Predicate>();
		Predicate predicate1 = criteriaBuilder.like(root.get("role"), "%" + "ROLE_ADMIN" + "%");
		Predicate predicate2 = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
				"%" + searchUserDTO.getKeyword().toLowerCase() + "%");
		predicates.add(criteriaBuilder.and(predicate1, predicate2));

		TypedQuery<Long> query = entityManager.createQuery(
				criteriaQuery.select(criteriaBuilder.count(root)).where(predicates.toArray(new Predicate[] {})));
		return query.getSingleResult();
	}

	@Override
	public Long countTotal(SearchUserDTO searchUserDTO) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<User> root = criteriaQuery.from(User.class);

		TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery.select(criteriaBuilder.count(root)));
		return typedQuery.getSingleResult();
	}

}

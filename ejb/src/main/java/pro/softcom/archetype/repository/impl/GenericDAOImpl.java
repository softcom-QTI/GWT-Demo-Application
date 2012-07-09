package pro.softcom.archetype.repository.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import pro.softcom.archetype.constants.AppServerConstants;
import pro.softcom.archetype.repository.GenericDAO;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class GenericDAOImpl<T> implements GenericDAO<T> {

	@PersistenceContext(unitName = AppServerConstants.PU_NAME)
	protected EntityManager entityManager;

	public void persist(T entity) {
		entityManager.persist(entity);
	}

	public void remove(T entity) {
		entityManager.remove(entity);
	}

	public T merge(T entity) {
		return entityManager.merge(entity);
	}

	public T find(Class<T> entityClass, Object primaryKey) {
		return entityManager.find(entityClass, primaryKey);
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll(Class<T> entityClass) {
		return entityManager.createQuery("from " + entityClass.getName())
				.getResultList();
	}
}

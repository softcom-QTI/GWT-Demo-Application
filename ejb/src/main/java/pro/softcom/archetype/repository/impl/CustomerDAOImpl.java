package pro.softcom.archetype.repository.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;

import pro.softcom.archetype.entity.Customer;
import pro.softcom.archetype.repository.CustomerDAO;
import pro.softcom.archetype.repository.util.QueryUtil;
import pro.softcom.archetype.service.CustomerSearchCriteria;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class CustomerDAOImpl extends GenericDAOImpl<Customer> implements CustomerDAO {

	@SuppressWarnings("unchecked")
	public List<Customer> searchCustomers(CustomerSearchCriteria criteria) {
		String queryString = "SELECT c From Customer c";

		// Prepare the query conditions
		if (criteria.getFirstname() != null) {
			queryString = QueryUtil.addCondition(queryString, "LOWER(c.firstname) LIKE ?1", true);
		}
		if (criteria.getLastname() != null) {
			queryString = QueryUtil.addCondition(queryString, "LOWER(c.lastname) LIKE ?2", true);
		}

		// Create the query
		Query query = entityManager.createQuery(queryString);

		// Set the query parameters
		if (criteria.getFirstname() != null) {
			query.setParameter(1, QueryUtil.getLowerCaseParameterWithWildcards(criteria.getFirstname()));
		}
		if (criteria.getLastname() != null) {
			query.setParameter(2, QueryUtil.getLowerCaseParameterWithWildcards(criteria.getLastname()));
		}

		return query.getResultList();
	}

}

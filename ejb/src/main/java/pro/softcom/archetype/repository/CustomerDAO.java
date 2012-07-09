package pro.softcom.archetype.repository;

import java.util.List;

import pro.softcom.archetype.entity.Customer;
import pro.softcom.archetype.service.CustomerSearchCriteria;


public interface CustomerDAO extends GenericDAO<Customer> {

	public List<Customer> searchCustomers(CustomerSearchCriteria criteria);

}

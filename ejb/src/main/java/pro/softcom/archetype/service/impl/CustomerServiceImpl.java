package pro.softcom.archetype.service.impl;

import java.util.List;
import java.util.Random;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import pro.softcom.archetype.entity.Customer;
import pro.softcom.archetype.repository.CustomerDAO;
import pro.softcom.archetype.service.CustomerSearchCriteria;
import pro.softcom.archetype.service.CustomerService;
import pro.softcom.archetype.service.util.Converter;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class CustomerServiceImpl implements CustomerService {

	@EJB
	private CustomerDAO customerDAO;

	public List<Customer> getAllCustomers() {
		return customerDAO.findAll(Customer.class);
	}

	public Customer saveCustomer(Customer customer) {
		return customerDAO.merge(Converter.convert(customer, Customer.class));
	}

	public List<Customer> searchCustomers(CustomerSearchCriteria criteria) {
		return customerDAO.searchCustomers(criteria);
	}

	public void initDatabase() {
		for (int i = 0; i < 10; i++) {
			customerDAO.persist(getCustomer());
		}
	}

	public static final String[] LASTNAMES = { "Carter", "Lincoln", "Obama", "Kennedy", "Roosevelt", "Nixon" };
	public static final String[] FIRSTNAMES = { "James", "Abraham", "Barrack", "John F.", "Theodore", "Richard" };

	private final static Random random = new Random();

	public static Customer getCustomer() {
		Customer customer = new Customer();

		customer.setFirstname(getRandomValue(FIRSTNAMES));
		customer.setLastname(getRandomValue(LASTNAMES));

		return customer;
	}

	private static String getRandomValue(String[] values) {
		return values[random.nextInt(values.length)];
	}

}

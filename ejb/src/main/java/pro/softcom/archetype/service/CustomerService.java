package pro.softcom.archetype.service;

import java.util.List;

import javax.ejb.Local;

import pro.softcom.archetype.entity.Customer;

@Local
public interface CustomerService {

    public List<Customer> getAllCustomers();

    public Customer saveCustomer(Customer customer);

    public List<Customer> searchCustomers(CustomerSearchCriteria criteria);

    public void initDatabase();
}

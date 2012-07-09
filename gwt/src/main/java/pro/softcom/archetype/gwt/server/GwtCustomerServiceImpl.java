package pro.softcom.archetype.gwt.server;

import java.util.List;

import pro.softcom.archetype.entity.Customer;
import pro.softcom.archetype.gwt.shared.model.CustomerModel;
import pro.softcom.archetype.gwt.shared.model.CustomerSearchCriteriaModel;
import pro.softcom.archetype.gwt.shared.rpc.GwtCustomerService;
import pro.softcom.archetype.service.CustomerSearchCriteria;
import pro.softcom.archetype.service.CustomerService;
import pro.softcom.archetype.service.util.Converter;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Inject;

public class GwtCustomerServiceImpl extends RemoteServiceServlet implements GwtCustomerService {

	private static final long serialVersionUID = 7269730142944540499L;

	private CustomerService customerService;

	@Inject
	public GwtCustomerServiceImpl(CustomerService customerService) {
		this.customerService = customerService;
	}

	public List<CustomerModel> searchCustomers(CustomerSearchCriteriaModel criteria) {
		return Converter.convert(customerService.searchCustomers(Converter.convert(criteria, CustomerSearchCriteria.class)), CustomerModel.class);
	}

	public void initDatabase() {
		customerService.initDatabase();
	}

	public CustomerModel saveCustomer(CustomerModel customer) {
		return Converter.convert(customerService.saveCustomer(Converter.convert(customer, Customer.class)), CustomerModel.class);
	}

}

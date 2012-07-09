package pro.softcom.archetype.gwt.shared.rpc;

import java.util.List;

import pro.softcom.archetype.gwt.shared.model.CustomerModel;
import pro.softcom.archetype.gwt.shared.model.CustomerSearchCriteriaModel;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("../services/CustomerService")
public interface GwtCustomerService extends RemoteService {
	public List<CustomerModel> searchCustomers(
			CustomerSearchCriteriaModel criteria);

	public CustomerModel saveCustomer(CustomerModel customer);
	
	public void initDatabase();
}

package pro.softcom.archetype.gwt.shared.rpc;

import java.util.List;

import pro.softcom.archetype.gwt.shared.model.CustomerModel;
import pro.softcom.archetype.gwt.shared.model.CustomerSearchCriteriaModel;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GwtCustomerServiceAsync {
	public void searchCustomers(CustomerSearchCriteriaModel criteria,
			AsyncCallback<List<CustomerModel>> callback);
	
	public void saveCustomer(CustomerModel customer, AsyncCallback<CustomerModel> callback);

	public void initDatabase(AsyncCallback<Void> callback);
}

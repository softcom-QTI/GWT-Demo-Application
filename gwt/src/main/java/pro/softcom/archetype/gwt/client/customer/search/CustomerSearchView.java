package pro.softcom.archetype.gwt.client.customer.search;

import java.util.List;

import pro.softcom.archetype.gwt.shared.model.CustomerModel;
import pro.softcom.archetype.gwt.shared.model.CustomerSearchCriteriaModel;

import com.google.gwt.user.client.ui.IsWidget;

public interface CustomerSearchView extends IsWidget {

	public void setPresenter(Presenter presenter);

	public void setCustomers(List<CustomerModel> customers);

	public void setMessage(String text);
	
	public interface Presenter {
		public void searchCustomers(CustomerSearchCriteriaModel criteria);
		
		public void editCustomer(CustomerModel customer);
	}
}

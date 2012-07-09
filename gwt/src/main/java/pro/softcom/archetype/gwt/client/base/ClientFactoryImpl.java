package pro.softcom.archetype.gwt.client.base;

import pro.softcom.archetype.gwt.client.customer.edit.CustomerEditView;
import pro.softcom.archetype.gwt.client.customer.search.CustomerSearchView;
import pro.softcom.archetype.gwt.shared.rpc.GwtCustomerServiceAsync;

import com.google.gwt.place.shared.PlaceController;
import com.google.inject.Inject;

public class ClientFactoryImpl implements ClientFactory {

	@Inject
	private PlaceController placeController;

	@Inject
	private CustomerSearchView customerSearchView;

	@Inject
	private CustomerEditView customerEditView;

	@Inject
	private GwtCustomerServiceAsync gwtCustomerServiceAsync;

	public PlaceController getPlaceController() {
		return placeController;
	}

	public CustomerSearchView getCustomerSearchView() {
		return customerSearchView;
	}

	public CustomerEditView getCustomerEditView() {
		return customerEditView;
	}

	public GwtCustomerServiceAsync gwtCustomerServiceAsync() {
		return gwtCustomerServiceAsync;
	}

}

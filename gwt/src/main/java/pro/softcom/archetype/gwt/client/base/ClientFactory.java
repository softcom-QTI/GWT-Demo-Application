package pro.softcom.archetype.gwt.client.base;

import pro.softcom.archetype.gwt.client.customer.edit.CustomerEditView;
import pro.softcom.archetype.gwt.client.customer.search.CustomerSearchView;
import pro.softcom.archetype.gwt.shared.rpc.GwtCustomerServiceAsync;

import com.google.gwt.place.shared.PlaceController;

public interface ClientFactory {

	// Place controller
	public PlaceController getPlaceController();

	// Views
	public CustomerSearchView getCustomerSearchView();
	public CustomerEditView getCustomerEditView();

	// Asynchronous interfaces
	public GwtCustomerServiceAsync gwtCustomerServiceAsync();

}

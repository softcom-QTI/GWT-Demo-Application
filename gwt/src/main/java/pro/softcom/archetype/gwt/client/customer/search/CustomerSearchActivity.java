package pro.softcom.archetype.gwt.client.customer.search;

import java.util.List;

import pro.softcom.archetype.gwt.client.base.ArchetypeActivity;
import pro.softcom.archetype.gwt.client.base.ClientFactory;
import pro.softcom.archetype.gwt.client.place.CustomerEditPlace;
import pro.softcom.archetype.gwt.client.place.CustomerSearchPlace;
import pro.softcom.archetype.gwt.shared.model.CustomerModel;
import pro.softcom.archetype.gwt.shared.model.CustomerSearchCriteriaModel;
import pro.softcom.archetype.gwt.shared.rpc.GwtCustomerServiceAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class CustomerSearchActivity extends ArchetypeActivity implements CustomerSearchView.Presenter {

    private CustomerSearchView customerView;
    private GwtCustomerServiceAsync customerService;

    public CustomerSearchActivity(CustomerSearchPlace place, ClientFactory clientFactory) {
        super(clientFactory.getCustomerSearchView(), clientFactory.getPlaceController());
        this.customerView = clientFactory.getCustomerSearchView();
        this.customerService = clientFactory.gwtCustomerServiceAsync();
        customerView.setPresenter(this);
    }

    @Override
    public void doOnStart() {
        // Do nothing
    }

    public void searchCustomers(CustomerSearchCriteriaModel criteria) {
        customerService.searchCustomers(criteria, new AsyncCallback<List<CustomerModel>>() {
            public void onSuccess(List<CustomerModel> result) {
                customerView.setCustomers(result);
            }

            public void onFailure(Throwable caught) {
                customerView.setMessage("An error occured during the search ! " + caught.getMessage());
            }
        });
    }

    public void editCustomer(CustomerModel customer) {
        goTo(new CustomerEditPlace(customer));
    }

}

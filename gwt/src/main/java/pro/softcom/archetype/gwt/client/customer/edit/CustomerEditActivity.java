package pro.softcom.archetype.gwt.client.customer.edit;

import pro.softcom.archetype.gwt.client.base.ArchetypeActivity;
import pro.softcom.archetype.gwt.client.base.ClientFactory;
import pro.softcom.archetype.gwt.client.place.CustomerEditPlace;
import pro.softcom.archetype.gwt.client.place.CustomerSearchPlace;
import pro.softcom.archetype.gwt.shared.model.CustomerModel;
import pro.softcom.archetype.gwt.shared.rpc.GwtCustomerServiceAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class CustomerEditActivity extends ArchetypeActivity implements CustomerEditView.Presenter {

    private CustomerEditView customerView;
    private GwtCustomerServiceAsync customerService;

    public CustomerEditActivity(CustomerEditPlace place, ClientFactory clientFactory) {
        super(clientFactory.getCustomerEditView(), clientFactory.getPlaceController());
        this.customerView = clientFactory.getCustomerEditView();
        this.customerService = clientFactory.gwtCustomerServiceAsync();
        customerView.setPresenter(this);
        customerView.setCustomer(place.getCustomer());
    }

    @Override
    public void doOnStart() {
        // Do nothing
    }

    public void saveCustomer(CustomerModel customer) {
        customerService.saveCustomer(customer, new AsyncCallback<CustomerModel>() {
            public void onSuccess(CustomerModel result) {
                goTo(new CustomerSearchPlace());
            }

            public void onFailure(Throwable caught) {
                customerView.setMessage("An error occurred while saving the customer ! " + caught.getMessage());
            }
        });
    }

    public void cancel() {
        goTo(new CustomerSearchPlace());
    }
}

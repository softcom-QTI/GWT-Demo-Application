package pro.softcom.archetype.gwt.client.customer.edit;

import pro.softcom.archetype.gwt.client.base.ArchetypeActivity;
import pro.softcom.archetype.gwt.client.place.CustomerEditPlace;
import pro.softcom.archetype.gwt.client.place.CustomerSearchPlace;
import pro.softcom.archetype.gwt.shared.model.CustomerModel;
import pro.softcom.archetype.gwt.shared.rpc.GwtCustomerServiceAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

public class CustomerEditActivity extends ArchetypeActivity implements CustomerEditView.Presenter {

    @Inject
    private CustomerEditView customerView;

    @Inject
    private GwtCustomerServiceAsync customerService;

    @Override
    public void doOnStart(AcceptsOneWidget panel) {
        panel.setWidget(customerView);
        customerView.setPresenter(this);
        customerView.setCustomer(((CustomerEditPlace) getCurrentPlace()).getCustomer());
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

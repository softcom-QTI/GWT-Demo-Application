package pro.softcom.archetype.gwt.client.customer.search;

import java.util.List;

import pro.softcom.archetype.gwt.client.base.ArchetypeActivity;
import pro.softcom.archetype.gwt.client.base.ArchetypeAsyncCallback;
import pro.softcom.archetype.gwt.client.place.CustomerEditPlace;
import pro.softcom.archetype.gwt.shared.model.CustomerModel;
import pro.softcom.archetype.gwt.shared.model.CustomerSearchCriteriaModel;
import pro.softcom.archetype.gwt.shared.rpc.GwtCustomerServiceAsync;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

public class CustomerSearchActivity extends ArchetypeActivity implements CustomerSearchView.Presenter {

    @Inject
    private CustomerSearchView customerView;

    @Inject
    private GwtCustomerServiceAsync customerService;

    @Override
    public void doOnStart(AcceptsOneWidget panel) {
        panel.setWidget(customerView);
        customerView.setPresenter(this);
    }

    public void searchCustomers(CustomerSearchCriteriaModel criteria) {
        customerService.searchCustomers(criteria, new ArchetypeAsyncCallback<List<CustomerModel>>(getEventBus()) {
            public void doOnSuccess(List<CustomerModel> result) {
                customerView.setCustomers(result);
            }

            public void doOnFailure(Throwable caught) {
                customerView.setMessage("An error occured during the search ! " + caught.getMessage());
            }
        });
    }

    public void editCustomer(CustomerModel customer) {
        goTo(new CustomerEditPlace(customer));
    }

}

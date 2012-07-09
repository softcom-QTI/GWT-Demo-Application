package pro.softcom.archetype.gwt.client.customer.edit;

import pro.softcom.archetype.gwt.shared.model.CustomerModel;

import com.google.gwt.user.client.ui.IsWidget;

public interface CustomerEditView extends IsWidget {

    public void setPresenter(Presenter presenter);

    public void setCustomer(CustomerModel customer);

    public void setMessage(String text);

    public interface Presenter {
        public void saveCustomer(CustomerModel customer);

        public void cancel();
    }
}

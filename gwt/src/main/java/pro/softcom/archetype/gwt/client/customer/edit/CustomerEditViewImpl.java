package pro.softcom.archetype.gwt.client.customer.edit;

import pro.softcom.archetype.gwt.shared.model.CustomerModel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class CustomerEditViewImpl extends Composite implements CustomerEditView {

    interface Binder extends UiBinder<Widget, CustomerEditViewImpl> {
    }

    private Presenter presenter;

    private CustomerModel customer;

    @UiField
    TextBox firstname;

    @UiField
    TextBox lastname;

    @UiField
    DateBox birthdate;

    @UiField
    Label message;

    @UiField
    Button save;

    @UiField
    Button cancel;

    @UiHandler("save")
    void saveCustomer(ClickEvent event) {
        if (customer == null) {
            customer = new CustomerModel();
        }

        // Update the fields
        customer.setFirstname(firstname.getText());
        customer.setLastname(lastname.getText());
        customer.setBirthdate(birthdate.getValue());

        // Call the presenter
        presenter.saveCustomer(customer);
    }

    @UiHandler("cancel")
    void cancel(ClickEvent event) {
        presenter.cancel();
    }

    public CustomerEditViewImpl() {
        // Initialize the UI
        Widget widget = GWT.<Binder> create(Binder.class).createAndBindUi(this);
        initWidget(widget);
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    public void setCustomer(CustomerModel c) {
        customer = c;

        if (customer != null) {
            // Display the fields
            firstname.setText(customer.getFirstname());
            lastname.setText(customer.getLastname());
            birthdate.setValue(customer.getBirthdate());
        }
    }

    public void setMessage(String text) {
        message.setText(text);
    }
}

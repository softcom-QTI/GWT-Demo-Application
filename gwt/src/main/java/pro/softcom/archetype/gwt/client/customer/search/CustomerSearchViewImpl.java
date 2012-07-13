package pro.softcom.archetype.gwt.client.customer.search;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

import pro.softcom.archetype.gwt.client.lib.table.ArchetypeCellTable;
import pro.softcom.archetype.gwt.shared.model.CustomerModel;
import pro.softcom.archetype.gwt.shared.model.CustomerSearchCriteriaModel;

import com.google.gwt.cell.client.DateCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;

public class CustomerSearchViewImpl extends Composite implements CustomerSearchView {

    interface Binder extends UiBinder<Widget, CustomerSearchViewImpl> {
    }

    private CustomerSearchI18n constants = GWT.create(CustomerSearchI18n.class);

    private Presenter presenter;

    @UiField
    TextBox lastname;

    @UiField
    TextBox firstname;

    @UiField
    Label message;

    @UiField
    Button create;

    @UiField
    Button search;

    @UiField(provided = true)
    ArchetypeCellTable<CustomerModel> customers = new ArchetypeCellTable<CustomerModel>();

    @UiHandler("search")
    void onSearch(ClickEvent event) {
        search();
    }

    @UiHandler("create")
    void onCreate(ClickEvent event) {
        presenter.editCustomer(new CustomerModel());
    }

    @UiHandler("lastname")
    void onEnterPressedLastname(KeyUpEvent event) {
        if (KeyCodes.KEY_ENTER == event.getNativeKeyCode()) {
            search();
        }
    }

    @UiHandler("firstname")
    void onEnterPressedFirstname(KeyUpEvent event) {
        if (KeyCodes.KEY_ENTER == event.getNativeKeyCode()) {
            search();
        }
    }

    public CustomerSearchViewImpl() {
        // Initialize the UI
        Widget widget = GWT.<Binder> create(Binder.class).createAndBindUi(this);
        initWidget(widget);

        customers.setPagerEnabled(true);

        // Initialize the table
        initTable();
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    public void setCustomers(List<CustomerModel> customers) {
        this.customers.setList(customers);
    }

    public void setMessage(String text) {
        message.setText(text);
    }

    private void initTable() {
        customers.addColumn(new TextColumn<CustomerModel>() {
            @Override
            public String getValue(CustomerModel customer) {
                return customer.getFirstname();
            }
        }, constants.firstname(), new Comparator<CustomerModel>() {
            @Override
            public int compare(CustomerModel o1, CustomerModel o2) {
                return o1.getFirstname().compareTo(o2.getFirstname());
            }
        });

        customers.addColumn(new TextColumn<CustomerModel>() {
            @Override
            public String getValue(CustomerModel customer) {
                return customer.getLastname();
            }
        }, constants.lastname());

        customers.addColumn(new Column<CustomerModel, Date>(new DateCell()) {
            @Override
            public Date getValue(CustomerModel object) {
                return object.getBirthdate();
            }
        }, constants.birthdate());

        // Add the selection handler
        customers.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            public void onSelectionChange(SelectionChangeEvent event) {
                presenter.editCustomer(customers.getSelectedObject());
            }
        });
    }

    private void search() {
        CustomerSearchCriteriaModel criteria = new CustomerSearchCriteriaModel();

        criteria.setFirstname(firstname.getText());
        criteria.setLastname(lastname.getText());

        presenter.searchCustomers(criteria);
    }

}

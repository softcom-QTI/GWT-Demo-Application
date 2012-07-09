package pro.softcom.archetype.gwt.client.customer.search;

import java.util.Date;
import java.util.List;

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
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

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
    CellTable<CustomerModel> customers = new CellTable<CustomerModel>();

    ListDataProvider<CustomerModel> dataProvider = new ListDataProvider<CustomerModel>();

    @UiField
    SimplePager pager;

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

        // Link the data provider and the table
        dataProvider.addDataDisplay(customers);

        // Initialize the table
        initTable();

        // Link the table to the pager
        pager.setDisplay(customers);
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    public void setCustomers(List<CustomerModel> customers) {
        dataProvider.setList(customers);
    }

    public void setMessage(String text) {
        message.setText(text);
    }

    private void initTable() {
        TextColumn<CustomerModel> firstname = new TextColumn<CustomerModel>() {
            @Override
            public String getValue(CustomerModel customer) {
                return customer.getFirstname();
            }
        };

        TextColumn<CustomerModel> lastname = new TextColumn<CustomerModel>() {
            @Override
            public String getValue(CustomerModel customer) {
                return customer.getLastname();
            }
        };

        Column<CustomerModel, Date> birthdate = new Column<CustomerModel, Date>(new DateCell()) {
            @Override
            public Date getValue(CustomerModel object) {
                return object.getBirthdate();
            }
        };

        customers.addColumn(firstname, constants.firstname());
        customers.addColumn(lastname, constants.lastname());
        customers.addColumn(birthdate, constants.birthdate());

        // Add the selection handler
        final SingleSelectionModel<CustomerModel> selectionModel = new SingleSelectionModel<CustomerModel>();
        customers.setSelectionModel(selectionModel);
        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            public void onSelectionChange(SelectionChangeEvent event) {
                presenter.editCustomer(selectionModel.getSelectedObject());
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

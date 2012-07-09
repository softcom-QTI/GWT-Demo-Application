package pro.softcom.archetype.gwt.client.base;

import pro.softcom.archetype.gwt.client.inject.ArchetypeInjector;
import pro.softcom.archetype.gwt.shared.rpc.GwtCustomerService;
import pro.softcom.archetype.gwt.shared.rpc.GwtCustomerServiceAsync;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootLayoutPanel;

public class ArchetypeEntryPoint implements EntryPoint {

    private final ArchetypeInjector injector = GWT.create(ArchetypeInjector.class);

    public void onModuleLoad() {
        GwtCustomerServiceAsync customerService = (GwtCustomerServiceAsync) GWT.create(GwtCustomerService.class);

        // Initialize the database with some customers
        customerService.initDatabase(new AsyncCallback<Void>() {
            public void onSuccess(Void result) {
                injector.getArchetypeApp().run(RootLayoutPanel.get());
            }

            public void onFailure(Throwable caught) {
            }
        });
    }
}

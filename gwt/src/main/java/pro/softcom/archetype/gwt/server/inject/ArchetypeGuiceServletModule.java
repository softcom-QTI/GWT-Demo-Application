package pro.softcom.archetype.gwt.server.inject;

import static com.google.inject.jndi.JndiIntegration.fromJndi;

import javax.naming.Context;
import javax.naming.InitialContext;

import pro.softcom.archetype.gwt.server.GwtCustomerServiceImpl;
import pro.softcom.archetype.gwt.server.rpc.GwtRpcDispatcher;
import pro.softcom.archetype.gwt.shared.rpc.GwtCustomerService;
import pro.softcom.archetype.service.CustomerService;

import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;

public class ArchetypeGuiceServletModule extends ServletModule {

    @Override
    protected void configureServlets() {
        // GWT side
        serve("/services/*").with(GwtRpcDispatcher.class);
        bind(GwtCustomerService.class).to(GwtCustomerServiceImpl.class);

        // Server side
        bind(Context.class).to(InitialContext.class).in(Singleton.class);
        bind(CustomerService.class).toProvider(fromJndi(CustomerService.class, "java:comp/env/ejb/CustomerService"));

    }
}

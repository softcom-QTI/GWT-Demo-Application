package pro.softcom.archetype.gwt.server.inject;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class GwtGuiceContextListener extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() {
		return Guice.createInjector(new ArchetypeGuiceServletModule());
	}
}
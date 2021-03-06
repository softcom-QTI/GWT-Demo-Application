package pro.softcom.archetype.gwt.client.inject;

import pro.softcom.archetype.gwt.client.customer.edit.CustomerEditView;
import pro.softcom.archetype.gwt.client.customer.edit.CustomerEditViewImpl;
import pro.softcom.archetype.gwt.client.customer.search.CustomerSearchView;
import pro.softcom.archetype.gwt.client.customer.search.CustomerSearchViewImpl;
import pro.softcom.archetype.gwt.client.place.ArchetypePlaceHistoryMapper;
import pro.softcom.archetype.gwt.client.place.CustomerSearchPlace;
import pro.softcom.archetype.gwt.client.skill.manage.SkillManageView;
import pro.softcom.archetype.gwt.client.skill.manage.SkillManageViewImpl;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class ArchetypeModule extends AbstractGinModule {

    @Override
    protected void configure() {
        // Common bindings
        bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
        bind(PlaceHistoryMapper.class).to(ArchetypePlaceHistoryMapper.class).in(Singleton.class);

        // Views
        bind(SkillManageView.class).to(SkillManageViewImpl.class).in(Singleton.class);
        bind(CustomerSearchView.class).to(CustomerSearchViewImpl.class).in(Singleton.class);
        bind(CustomerEditView.class).to(CustomerEditViewImpl.class).in(Singleton.class);
    }

    @Provides
    @Singleton
    @SuppressWarnings("deprecation")
    public PlaceHistoryHandler getHistoryHandler(PlaceController placeController, PlaceHistoryMapper historyMapper, EventBus eventBus) {
        PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
        historyHandler.register(placeController, eventBus, new CustomerSearchPlace());
        return historyHandler;
    }

    @Provides
    @Singleton
    @SuppressWarnings("deprecation")
    public PlaceController getPlaceController(EventBus eventBus) {
        return new PlaceController(eventBus);
    }

}

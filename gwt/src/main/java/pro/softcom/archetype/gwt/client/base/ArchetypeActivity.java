package pro.softcom.archetype.gwt.client.base;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;

public abstract class ArchetypeActivity extends AbstractActivity {

    private EventBus eventBus;
    private IsWidget widget;

    @Inject
    private PlaceController placeController;

    public final void start(AcceptsOneWidget panel, EventBus eventBus) {
        this.eventBus = eventBus;
        panel.setWidget(widget);
        doOnStart(panel);
    }

    public abstract void doOnStart(AcceptsOneWidget panel);

    public EventBus getEventBus() {
        return eventBus;
    }

    public void goTo(Place place) {
        placeController.goTo(place);
    }

    public Place getCurrentPlace() {
        return placeController.getWhere();
    }

}

package pro.softcom.archetype.gwt.client.base;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;

public abstract class ArchetypeActivity extends AbstractActivity {

    private EventBus eventBus;
    private PlaceController placeController;
    private IsWidget widget;

    public ArchetypeActivity(IsWidget widget, PlaceController placeController) {
        this.placeController = placeController;
        this.widget = widget;
    }

    public final void start(AcceptsOneWidget panel, EventBus eventBus) {
        this.eventBus = eventBus;
        panel.setWidget(widget);
        doOnStart();
    }

    public abstract void doOnStart();

    public EventBus getEventBus() {
        return eventBus;
    }

    public void goTo(Place place) {
        placeController.goTo(place);
    }

}

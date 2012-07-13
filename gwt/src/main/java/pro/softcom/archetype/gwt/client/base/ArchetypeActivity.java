package pro.softcom.archetype.gwt.client.base;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

/**
 * This class must be used as the superclass of all activities, as it defines some default behaviors.
 */
public abstract class ArchetypeActivity extends AbstractActivity {

    private EventBus eventBus;

    @Inject
    private PlaceController placeController;

    public final void start(AcceptsOneWidget panel, EventBus eventBus) {
        this.eventBus = eventBus;
        doOnStart(panel);
    }

    /**
     * This method is executed when the activity starts, and can be used to initialize components and/or do custom actions.</br>
     * The given panel is a reference to the content panel from the base structure of the application,
     * and a view/component should be set as a widget to it in order to display something.
     *
     * @param panel A reference to the content panel of the base structure of the application.
     */
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

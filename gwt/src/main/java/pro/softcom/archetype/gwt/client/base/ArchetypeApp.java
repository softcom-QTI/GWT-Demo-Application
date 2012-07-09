package pro.softcom.archetype.gwt.client.base;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.inject.Inject;

public class ArchetypeApp {
    private static final Logger log = Logger.getLogger(ArchetypeApp.class.getName());

    @Inject
    private PlaceHistoryHandler placeHistoryHandler;

    @Inject
    private ArchetypeActivityMapper archetypeActivityMapper;

    @Inject
    private EventBus eventBus;

    public void run(HasWidgets root) {
        GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
            public void onUncaughtException(Throwable e) {
                log.log(Level.SEVERE, e.getMessage(), e);
            }
        });

        // This is the root panel that will contain the different views
        SimplePanel panel = new SimplePanel();

        ActivityManager mainActivityManager = new ActivityManager(archetypeActivityMapper, eventBus);
        mainActivityManager.setDisplay(panel);

        // Browser history integration
        placeHistoryHandler.handleCurrentHistory();

        root.add(panel);
    }
}

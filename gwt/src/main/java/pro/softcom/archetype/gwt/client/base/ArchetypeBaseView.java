package pro.softcom.archetype.gwt.client.base;

import java.util.HashMap;
import java.util.Map;

import pro.softcom.archetype.gwt.client.lib.event.HighlightMenuEvent;
import pro.softcom.archetype.gwt.client.lib.event.HighlightMenuHandler;
import pro.softcom.archetype.gwt.client.lib.event.LoadingEvent;
import pro.softcom.archetype.gwt.client.lib.event.MessageEvent;
import pro.softcom.archetype.gwt.client.lib.panel.MessagePanel;
import pro.softcom.archetype.gwt.client.place.CustomerEditPlace;
import pro.softcom.archetype.gwt.client.place.CustomerSearchPlace;
import pro.softcom.archetype.gwt.shared.model.CustomerModel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasOneWidget;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/**
 * This component contains the base structure of the web app, with a header, a menu, and a panel that will contains the different places.
 */
public class ArchetypeBaseView extends Composite {

    interface ArchetypeBaseViewUiBinder extends UiBinder<Widget, ArchetypeBaseView> {
    }

    private static ArchetypeBaseViewUiBinder uiBinder = GWT.create(ArchetypeBaseViewUiBinder.class);

    private ArchetypeBaseI18n constants = GWT.create(ArchetypeBaseI18n.class);

    @UiField
    SimplePanel contentPanel;

    @UiField
    Image loading;

    @UiField
    MenuBar menuBar;

    @UiField
    MessagePanel messagePanel;

    @Inject
    public ArchetypeBaseView(final PlaceController placeController, EventBus eventBus) {
        initWidget(uiBinder.createAndBindUi(this));

        // Hide the loading image by default
        loading.getElement().getStyle().setDisplay(Display.NONE);

        // Register global events
        LoadingEvent.register(eventBus, new LoadingEvent.Handler() {
            @Override
            public void onLoading(final LoadingEvent event) {
                // Hide/display the loading image
                if (event.isLoading()) {
                    loading.getElement().getStyle().setDisplay(Display.BLOCK);
                } else {
                    loading.getElement().getStyle().setDisplay(Display.NONE);
                }
            }
        });

        MessageEvent.register(eventBus, new MessageEvent.Handler() {
            @Override
            public void onMessageEvent(MessageEvent event) {
                messagePanel.addMessages(event.getMessages());
            }
        });

        // Create the handler for the menu highlighting
        ArchetypeMenuHighlightHandler handler = new ArchetypeMenuHighlightHandler();
        HighlightMenuEvent.register(eventBus, handler);

        // Create the menu bar items
        createHomeMenu(placeController, handler);
        createEmployeesMenu(placeController, handler);
        createSkillsMenu(placeController, handler);
        createProjectsMenu(placeController, handler);
        createPlanningMenu(placeController, handler);
    }

    private void createHomeMenu(final PlaceController placeController, ArchetypeMenuHighlightHandler handler) {
        MenuItem menuItem = new MenuItem(constants.menuHome(), new Command() {
            @Override
            public void execute() {
                // TODO
            }
        });

        menuBar.addItem(menuItem);

        handler.addMenuItem(ArchetypeMenuConstants.HOME, menuItem);
    }

    private void createEmployeesMenu(final PlaceController placeController, ArchetypeMenuHighlightHandler handler) {
        MenuItem menuItem = new MenuItem(constants.menuEmployees(), new Command() {
            @Override
            public void execute() {
                placeController.goTo(new CustomerSearchPlace());
            }
        });

        menuBar.addItem(menuItem);

        handler.addMenuItem(ArchetypeMenuConstants.EMPLOYEES, menuItem);
    }

    private void createSkillsMenu(final PlaceController placeController, ArchetypeMenuHighlightHandler handler) {
        MenuItem menuItem = new MenuItem(constants.menuSkills(), new Command() {
            @Override
            public void execute() {
                placeController.goTo(new CustomerEditPlace(new CustomerModel()));
            }
        });

        menuBar.addItem(menuItem);

        handler.addMenuItem(ArchetypeMenuConstants.SKILLS, menuItem);
    }

    private void createProjectsMenu(final PlaceController placeController, ArchetypeMenuHighlightHandler handler) {
        MenuItem menuItem = new MenuItem(constants.menuProjects(), new Command() {
            @Override
            public void execute() {
                // TODO
            }
        });

        menuBar.addItem(menuItem);

        handler.addMenuItem(ArchetypeMenuConstants.PROJECTS, menuItem);
    }

    private void createPlanningMenu(final PlaceController placeController, ArchetypeMenuHighlightHandler handler) {
        MenuItem menuItem = new MenuItem(constants.menuPlanning(), new Command() {
            @Override
            public void execute() {
                // TODO
            }
        });

        menuBar.addItem(menuItem);

        handler.addMenuItem(ArchetypeMenuConstants.PLANNING, menuItem);
    }

    /**
     * Return the panel where the content of the page will be displayed.
     *
     * @return
     */
    public HasOneWidget getContentPanel() {
        return contentPanel;
    }

    /**
     * Custom handler that will allow the highlighting of the currently selected menu item.
     */
    private class ArchetypeMenuHighlightHandler implements HighlightMenuHandler {

        private Map<String, MenuItem> menuItems = new HashMap<String, MenuItem>();

        @Override
        public void onHighlight(HighlightMenuEvent event) {
            // Remove "highlight" style on all menu items
            for (MenuItem menuItem : menuItems.values()) {
                menuItem.removeStyleDependentName("highlighted");
            }

            // Highlight the given menu item
            MenuItem menuItem = menuItems.get(event.getMenuIdentifier());

            if (menuItem != null) {
                menuItem.addStyleDependentName("highlighted");
            }
        }

        public void addMenuItem(String placeTokenName, MenuItem menuItem) {
            menuItems.put(placeTokenName, menuItem);
        }
    }
}

package pro.softcom.archetype.gwt.client.base;

import java.util.HashMap;
import java.util.Map;

import pro.softcom.archetype.gwt.client.lib.event.HighlightMenuEvent;
import pro.softcom.archetype.gwt.client.lib.event.HighlightMenuHandler;
import pro.softcom.archetype.gwt.client.lib.event.LoadingEvent;
import pro.softcom.archetype.gwt.client.lib.event.MessageEvent;
import pro.softcom.archetype.gwt.client.lib.menu.SoftcomMenuBar;
import pro.softcom.archetype.gwt.client.lib.menu.SoftcomMenuItem;
import pro.softcom.archetype.gwt.client.lib.panel.MessagePanel;
import pro.softcom.archetype.gwt.client.place.SkillManagePlace;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasOneWidget;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/**
 * This component contains the base structure of the web app, with a header, a menu, and a panel that will contains the different places.
 */
public class ArchetypeBaseView extends Composite {



	private final class MenuShortcutsHandler implements NativePreviewHandler {
		@Override
		public void onPreviewNativeEvent(NativePreviewEvent event) {
			if (event.getTypeInt() == Event.ONKEYDOWN) {
				NativeEvent ne = event.getNativeEvent();
				if (ne.getCtrlKey() && ne.getShiftKey() && ne.getKeyCode() == 'H') {
					event.cancel();
					getHomeMenuItem().getCommand().execute();
					getMainMenuBar().selectItem(getHomeMenuItem());
				} else if (ne.getCtrlKey() && ne.getShiftKey() && ne.getKeyCode() == 'Q') {
					event.cancel();
					getSearchCollaboratorsMenuItem().getCommand().execute();
					getMainMenuBar().selectItem(getSearchMenuItem());
				} else if (ne.getCtrlKey() && ne.getShiftKey() && ne.getKeyCode() == 'A') {
					event.cancel();
					getSearchProjectsMenuItem().getCommand().execute();
					getMainMenuBar().selectItem(getSearchMenuItem());
				} else if (ne.getCtrlKey() && ne.getShiftKey() && ne.getKeyCode() == 'Y') {
					event.cancel();
					getSearchSkillsMenuItem().getCommand().execute();
					getMainMenuBar().selectItem(getSearchMenuItem());
				} else if (ne.getCtrlKey() && ne.getShiftKey() && ne.getKeyCode() == 'C') {
					event.cancel();
					getManageCollaboratorsMenuItem().getCommand().execute();
					getMainMenuBar().selectItem(getManageMenuItem());
				} else if (ne.getCtrlKey() && ne.getShiftKey() && ne.getKeyCode() == 'P') {
					event.cancel();
					getManageProjectsMenuItem().getCommand().execute();
					getMainMenuBar().selectItem(getManageMenuItem());
				} else if (ne.getCtrlKey() && ne.getShiftKey() && ne.getKeyCode() == 'S') {
					event.cancel();
					getManageSkillsMenuItem().getCommand().execute();
					getMainMenuBar().selectItem(getManageMenuItem());
				} else if (ne.getCtrlKey() && ne.getShiftKey() && ne.getKeyCode() == 'G') {
					event.cancel();
					getManageGroupsMenuItem().getCommand().execute();
					getMainMenuBar().selectItem(getManageMenuItem());
				} else if (ne.getCtrlKey() && ne.getShiftKey() && ne.getKeyCode() == 'F') {
					event.cancel();
					getManageFunctionsMenuItem().getCommand().execute();
					getMainMenuBar().selectItem(getManageMenuItem());
				} else if (ne.getCtrlKey() && ne.getShiftKey() && ne.getKeyCode() == 'E') {
					event.cancel();
					getHrEvaluationsMenuItem().getCommand().execute();
					getMainMenuBar().selectItem(getHrMenuItem());
				} else if (ne.getCtrlKey() && ne.getShiftKey() && ne.getKeyCode() == 'U') {
					event.cancel();
					getHrEducationMenuItem().getCommand().execute();
					getMainMenuBar().selectItem(getHrMenuItem());
				} else if (ne.getCtrlKey() && ne.getShiftKey() && ne.getKeyCode() == 'R') {
					event.cancel();
					getHrProfileCreationMenuItem().getCommand().execute();
					getMainMenuBar().selectItem(getHrMenuItem());
				} else if (ne.getCtrlKey() && ne.getShiftKey() && ne.getKeyCode() == 'N') {
					event.cancel();
					getAdminNewsMenuItem().getCommand().execute();
					getMainMenuBar().selectItem(getAdminMenuItem());
				}
			}
		}
	}
	
	interface ArchetypeBaseViewUiBinder extends UiBinder<Widget, ArchetypeBaseView> {
    }

    /**
     * Custom handler that will allow the highlighting of the currently selected menu item.
     */
    private class ArchetypeMenuHighlightHandler implements HighlightMenuHandler {
    
        private Map<String, SoftcomMenuItem> menuItems = new HashMap<String, SoftcomMenuItem>();
    
        @Override
        public void onHighlight(HighlightMenuEvent event) {
            // Remove "highlight" style on all menu items
            for (SoftcomMenuItem menuItem : menuItems.values()) {
                menuItem.removeStyleDependentName("highlighted");
            }
    
            // Highlight the given menu item
            SoftcomMenuItem menuItem = menuItems.get(event.getMenuIdentifier());
    
            if (menuItem != null) {
                menuItem.addStyleDependentName("highlighted");
            }
        }
    
        public void addMenuItem(String placeTokenName, SoftcomMenuItem menuItem) {
            menuItems.put(placeTokenName, menuItem);
        }
    }

    private static ArchetypeBaseViewUiBinder uiBinder = GWT.create(ArchetypeBaseViewUiBinder.class);

    private ArchetypeBaseI18n constants = GWT.create(ArchetypeBaseI18n.class);

    @UiField
    SimplePanel contentPanel;

    @UiField
    Image loading;

   /*
	 * Main Menu Bar
	 */
	@UiField(provided = true)
	SoftcomMenuBar mainMenuBar;
	
	/*
	 * Home Menu Bar
	 */
	private SoftcomMenuBar homeMenuBar;
	/*
	 * Search Menu Bar
	 */
	private SoftcomMenuBar searchMenuBar;
	/*
	 * Manage Menu Bar
	 */
	private SoftcomMenuBar manageMenuBar;
	/*
	 * HR Menu Bar
	 */
	private SoftcomMenuBar hrMenuBar;
	/*
	 * Admin Menu Bar
	 */
	private SoftcomMenuBar adminMenuBar;

	// Home Menuitems
	private SoftcomMenuItem homeMenuItem;

	// Search Menuitems
	private SoftcomMenuItem searchMenuItem;
	private SoftcomMenuItem searchCollaboratorsMenuItem;
	private SoftcomMenuItem searchProjectsMenuItem;
	private SoftcomMenuItem searchSkillsMenuItem;

	// Manage Menuitems
	private SoftcomMenuItem manageMenuItem;
	private SoftcomMenuItem manageCollaboratorsMenuItem;
	private SoftcomMenuItem manageProjectsMenuItem;
	private SoftcomMenuItem manageSkillsMenuItem;
	private SoftcomMenuItem manageGroupsMenuItem;
	private SoftcomMenuItem manageFunctionsMenuItem;

	// HR Menuitems
	private SoftcomMenuItem hrMenuItem;
	private SoftcomMenuItem hrEvaluationsMenuItem;
	private SoftcomMenuItem hrEducationMenuItem;
	private SoftcomMenuItem hrProfileCreationMenuItem;

	// Admin Menuitems
	private SoftcomMenuItem adminMenuItem;
	private SoftcomMenuItem adminNewsMenuItem;
    

    @UiField
    MessagePanel messagePanel;

	private PlaceController menuPlaceController = null;

    @Inject
    public ArchetypeBaseView(final PlaceController placeController, EventBus eventBus) {
		menuPlaceController = placeController;

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

		Event.addNativePreviewHandler(new MenuShortcutsHandler());

		initMainMenu(handler);

		// Menu Home
		initHomeMenu(handler);

		// Menu Search
		initSearchMenu(handler);

		// Menu Manage
		initManageMenu(handler);

		// Menu HR
		initHRMenu(handler);

		// Menu Admin
		initAdminMenu(handler);
    }

    /**
     * Return the panel where the content of the page will be displayed.
     *
     * @return
     */
    public HasOneWidget getContentPanel() {
        return contentPanel;
    }

    private void initMainMenu(ArchetypeMenuHighlightHandler handler) {
    	mainMenuBar = new SoftcomMenuBar();
		mainMenuBar.setAutoOpen(true);
		mainMenuBar.setVisible(true);
    }

    private void initHomeMenu(ArchetypeMenuHighlightHandler handler) {
    	homeMenuBar = new SoftcomMenuBar(true);
    	
        homeMenuItem = new SoftcomMenuItem(constants.menuHome(), new Command() {
            @Override
            public void execute() {
            	 menuPlaceController.goTo(new SkillManagePlace());
            }
        });
		setHomeMenuItem(homeMenuBar.addItem(homeMenuItem, "Ctrl+Shift+H"));
		handler.addMenuItem(ArchetypeMenuConstants.HOME, homeMenuItem);
    }

    private void initSearchMenu(ArchetypeMenuHighlightHandler handler) {
    	searchMenuBar = new SoftcomMenuBar(true);
    	
    	searchCollaboratorsMenuItem = new SoftcomMenuItem(constants.menuSearchCollaborators(), new Command() {
            @Override
            public void execute() {
                menuPlaceController.goTo(new SkillManagePlace());
            }
        });
		setSearchCollaboratorsMenuItem(searchMenuBar.addItem(searchCollaboratorsMenuItem, "Ctrl+Shift+Q"));
//
		setSearchMenuItem(mainMenuBar.addItem(constants.menuSearch(), searchMenuBar));
        

		handler.addMenuItem(ArchetypeMenuConstants.SEARCH_COLLABORATORS, searchCollaboratorsMenuItem);
		handler.addMenuItem(ArchetypeMenuConstants.SEARCH, searchMenuItem);
    }

    private void initManageMenu(ArchetypeMenuHighlightHandler handler) {
    	SoftcomMenuItem manageSkillsMenuItem = new SoftcomMenuItem(constants.menuManageSkills(), new Command() {
            @Override
            public void execute() {
                menuPlaceController.goTo(new SkillManagePlace());
            }
        });
    
        MenuBar manageMenuBar = new MenuBar(true);
        manageMenuBar.addItem(manageSkillsMenuItem);
        mainMenuBar.addItem(constants.menuManage(), manageMenuBar);
    
        handler.addMenuItem(ArchetypeMenuConstants.MANAGE_SKILLS, manageSkillsMenuItem);
    }

    private void initHRMenu(ArchetypeMenuHighlightHandler handler) {
        MenuBar hrMenuBar = new MenuBar(true);
        mainMenuBar.addItem(constants.menuHr(), hrMenuBar);
    }

    private void initAdminMenu(ArchetypeMenuHighlightHandler handler) {
        MenuBar adminMenuBar = new MenuBar(true);
        mainMenuBar.addItem(constants.menuAdmin(), adminMenuBar);
    }
	public SoftcomMenuBar getMainMenuBar() {
		return mainMenuBar;
	}

	public void setMainMenuBar(SoftcomMenuBar mainMenuBar) {
		this.mainMenuBar = mainMenuBar;
	}

	public SoftcomMenuBar getHomeMenuBar() {
		return homeMenuBar;
	}

	public void setHomeMenuBar(SoftcomMenuBar homeMenuBar) {
		this.homeMenuBar = homeMenuBar;
	}

	public SoftcomMenuBar getSearchMenuBar() {
		return searchMenuBar;
	}

	public void setSearchMenuBar(SoftcomMenuBar searchMenuBar) {
		this.searchMenuBar = searchMenuBar;
	}

	public SoftcomMenuBar getManageMenuBar() {
		return manageMenuBar;
	}

	public void setManageMenuBar(SoftcomMenuBar manageMenuBar) {
		this.manageMenuBar = manageMenuBar;
	}

	public SoftcomMenuBar getHrMenuBar() {
		return hrMenuBar;
	}

	public void setHrMenuBar(SoftcomMenuBar hrMenuBar) {
		this.hrMenuBar = hrMenuBar;
	}

	public SoftcomMenuBar getAdminMenuBar() {
		return adminMenuBar;
	}

	public void setAdminMenuBar(SoftcomMenuBar adminMenuBar) {
		this.adminMenuBar = adminMenuBar;
	}

	public SoftcomMenuItem getHomeMenuItem() {
		return homeMenuItem;
	}

	public void setHomeMenuItem(SoftcomMenuItem homeMenuItem) {
		this.homeMenuItem = homeMenuItem;
	}

	public SoftcomMenuItem getSearchMenuItem() {
		return searchMenuItem;
	}

	public void setSearchMenuItem(SoftcomMenuItem searchMenuItem) {
		this.searchMenuItem = searchMenuItem;
	}

	public SoftcomMenuItem getSearchProjectsMenuItem() {
		return searchProjectsMenuItem;
	}

	public void setSearchProjectsMenuItem(SoftcomMenuItem searchProjectsMenuItem) {
		this.searchProjectsMenuItem = searchProjectsMenuItem;
	}

	public SoftcomMenuItem getSearchSkillsMenuItem() {
		return searchSkillsMenuItem;
	}

	public void setSearchSkillsMenuItem(SoftcomMenuItem searchSkillsMenuItem) {
		this.searchSkillsMenuItem = searchSkillsMenuItem;
	}

	public SoftcomMenuItem getManageMenuItem() {
		return manageMenuItem;
	}

	public void setManageMenuItem(SoftcomMenuItem manageMenuItem) {
		this.manageMenuItem = manageMenuItem;
	}

	public SoftcomMenuItem getManageCollaboratorsMenuItem() {
		return manageCollaboratorsMenuItem;
	}

	public void setManageCollaboratorsMenuItem(
			SoftcomMenuItem manageCollaboratorsMenuItem) {
		this.manageCollaboratorsMenuItem = manageCollaboratorsMenuItem;
	}

	public SoftcomMenuItem getManageProjectsMenuItem() {
		return manageProjectsMenuItem;
	}

	public void setManageProjectsMenuItem(SoftcomMenuItem manageProjectsMenuItem) {
		this.manageProjectsMenuItem = manageProjectsMenuItem;
	}

	public SoftcomMenuItem getManageSkillsMenuItem() {
		return manageSkillsMenuItem;
	}

	public void setManageSkillsMenuItem(SoftcomMenuItem manageSkillsMenuItem) {
		this.manageSkillsMenuItem = manageSkillsMenuItem;
	}

	public SoftcomMenuItem getManageGroupsMenuItem() {
		return manageGroupsMenuItem;
	}

	public void setManageGroupsMenuItem(SoftcomMenuItem manageGroupsMenuItem) {
		this.manageGroupsMenuItem = manageGroupsMenuItem;
	}

	public SoftcomMenuItem getManageFunctionsMenuItem() {
		return manageFunctionsMenuItem;
	}

	public void setManageFunctionsMenuItem(SoftcomMenuItem manageFunctionsMenuItem) {
		this.manageFunctionsMenuItem = manageFunctionsMenuItem;
	}

	public SoftcomMenuItem getHrMenuItem() {
		return hrMenuItem;
	}

	public void setHrMenuItem(SoftcomMenuItem hrMenuItem) {
		this.hrMenuItem = hrMenuItem;
	}

	public SoftcomMenuItem getHrEvaluationsMenuItem() {
		return hrEvaluationsMenuItem;
	}

	public void setHrEvaluationsMenuItem(SoftcomMenuItem hrEvaluationsMenuItem) {
		this.hrEvaluationsMenuItem = hrEvaluationsMenuItem;
	}

	public SoftcomMenuItem getHrEducationMenuItem() {
		return hrEducationMenuItem;
	}

	public void setHrEducationMenuItem(SoftcomMenuItem hrEducationMenuItem) {
		this.hrEducationMenuItem = hrEducationMenuItem;
	}

	public SoftcomMenuItem getHrProfileCreationMenuItem() {
		return hrProfileCreationMenuItem;
	}

	public void setHrProfileCreationMenuItem(SoftcomMenuItem hrProfileCreationMenuItem) {
		this.hrProfileCreationMenuItem = hrProfileCreationMenuItem;
	}

	public SoftcomMenuItem getAdminMenuItem() {
		return adminMenuItem;
	}

	public void setAdminMenuItem(SoftcomMenuItem adminMenuItem) {
		this.adminMenuItem = adminMenuItem;
	}

	public SoftcomMenuItem getAdminNewsMenuItem() {
		return adminNewsMenuItem;
	}

	public void setAdminNewsMenuItem(SoftcomMenuItem adminNewsMenuItem) {
		this.adminNewsMenuItem = adminNewsMenuItem;
	}

	public PlaceController getMenuPlaceController() {
		return menuPlaceController;
	}

	public void setMenuPlaceController(PlaceController menuPlaceController) {
		this.menuPlaceController = menuPlaceController;
	}

	public SoftcomMenuItem getSearchCollaboratorsMenuItem() {
		return searchCollaboratorsMenuItem;
	}

	public void setSearchCollaboratorsMenuItem(
			SoftcomMenuItem searchCollaboratorsMenuItem) {
		this.searchCollaboratorsMenuItem = searchCollaboratorsMenuItem;
	}
}

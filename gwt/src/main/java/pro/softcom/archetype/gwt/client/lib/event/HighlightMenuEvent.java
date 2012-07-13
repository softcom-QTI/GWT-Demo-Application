package pro.softcom.archetype.gwt.client.lib.event;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

public class HighlightMenuEvent extends GwtEvent<HighlightMenuHandler> {

    private final String menuIdentifier;

    /**
     * Event type for click events. Represents the meta-data associated with this
     * event.
     */
    private final static Type<HighlightMenuHandler> TYPE = new Type<HighlightMenuHandler>();

    public HighlightMenuEvent(String menuIdentifier) {
        this.menuIdentifier = menuIdentifier;
    }

    @Override
    public Type<HighlightMenuHandler> getAssociatedType() {
        return TYPE;
    }

    public static HandlerRegistration register(EventBus eventBus, HighlightMenuHandler handler) {
        return eventBus.addHandler(TYPE, handler);
    }

    @Override
    protected void dispatch(HighlightMenuHandler handler) {
        handler.onHighlight(this);
    }

    public String getMenuIdentifier() {
        return menuIdentifier;
    }
}

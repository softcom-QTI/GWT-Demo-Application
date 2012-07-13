package pro.softcom.archetype.gwt.client.lib.event;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

public class LoadingEvent extends GwtEvent<LoadingEvent.Handler> {

    private final boolean loading;

    public LoadingEvent(boolean loading) {
        this.loading = loading;
    }

    public interface Handler extends EventHandler {
        void onLoading(LoadingEvent event);
    }

    private static final Type<Handler> TYPE = new Type<Handler>();

    public static HandlerRegistration register(EventBus eventBus, LoadingEvent.Handler handler) {
        return eventBus.addHandler(TYPE, handler);
    }

    @Override
    public GwtEvent.Type<Handler> getAssociatedType() {
        return TYPE;
    }

    public boolean isLoading() {
        return loading;
    }

    @Override
    protected void dispatch(Handler handler) {
        handler.onLoading(this);
    }

}

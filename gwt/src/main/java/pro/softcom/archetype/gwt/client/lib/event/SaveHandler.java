package pro.softcom.archetype.gwt.client.lib.event;

import com.google.gwt.event.shared.EventHandler;

public interface SaveHandler<T> extends EventHandler {
    void onSave(SaveEvent<T> event);
}

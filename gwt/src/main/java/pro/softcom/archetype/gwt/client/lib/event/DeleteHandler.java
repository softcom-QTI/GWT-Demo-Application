package pro.softcom.archetype.gwt.client.lib.event;

import com.google.gwt.event.shared.EventHandler;

public interface DeleteHandler<T> extends EventHandler {
    void onDelete(DeleteEvent<T> event);
}

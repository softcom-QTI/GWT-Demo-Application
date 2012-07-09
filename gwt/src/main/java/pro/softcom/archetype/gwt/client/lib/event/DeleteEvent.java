package pro.softcom.archetype.gwt.client.lib.event;

import com.google.gwt.event.shared.GwtEvent;

public class DeleteEvent<T> extends GwtEvent<DeleteHandler<T>> {

    private final T editedObject;

    /**
     * Event type for click events. Represents the meta-data associated with
     * this event.
     */
    private final Type<DeleteHandler<T>> TYPE = new Type<DeleteHandler<T>>();

    public DeleteEvent(T editedObject) {
        this.editedObject = editedObject;
    }

    @Override
    public Type<DeleteHandler<T>> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(DeleteHandler<T> handler) {
        handler.onDelete(this);
    }

    public T getEditedObject() {
        return editedObject;
    }

}
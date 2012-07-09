package pro.softcom.archetype.gwt.client.lib.event;

import com.google.gwt.event.shared.GwtEvent;

public class SaveEvent<T> extends GwtEvent<SaveHandler<T>> {

    private final T editedObject;
    private final boolean creation;

    /**
     * Event type for click events. Represents the meta-data associated with
     * this event.
     */
    private final Type<SaveHandler<T>> TYPE = new Type<SaveHandler<T>>();

    public SaveEvent(T editedObject, boolean creation) {
        this.editedObject = editedObject;
        this.creation = creation;
    }

    @Override
    public Type<SaveHandler<T>> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(SaveHandler<T> handler) {
        handler.onSave(this);
    }

    /**
     * Get the object that is going to be saved.
     * 
     * @return
     */
    public T getEditedObject() {
        return editedObject;
    }

    /**
     * Returns true if the object is a new one.
     * 
     * @return
     */
    public boolean isCreation() {
        return creation;
    }

}

package pro.softcom.archetype.gwt.client.base;

import java.util.logging.Level;
import java.util.logging.Logger;

import pro.softcom.archetype.gwt.client.lib.event.LoadingEvent;
import pro.softcom.archetype.gwt.client.lib.event.MessageEvent;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * This class must be used when calling GWT async services.
 * It will display/hide the loading image while waiting for the answer of the server.
 */
public abstract class ArchetypeAsyncCallback<T> implements AsyncCallback<T> {

    protected static final Logger logger = Logger.getLogger(ArchetypeAsyncCallback.class.getName());

    private EventBus eventBus;
    private boolean loading;

    public ArchetypeAsyncCallback(EventBus eventBus) {
        this(eventBus, true);
    }

    public ArchetypeAsyncCallback(EventBus eventBus, boolean showLoading) {
        this.eventBus = eventBus;
        this.loading = showLoading;
        if (loading) {
            // Show the loading image
            eventBus.fireEvent(new LoadingEvent(true));
        }
    }

    @Override
    public final void onSuccess(T result) {
        hideLoadingImage();

        // Delegate the "on success" to the custom implementation
        doOnSuccess(result);
    }

    @Override
    public final void onFailure(Throwable caught) {
        hideLoadingImage();

        // Log the error
        logger.log(Level.SEVERE, "An exception occured in the GWT-RPC remote call", caught);

        // Fire a message event to warn the user that an error occurred
        eventBus.fireEvent(new MessageEvent("An exception occured in the GWT-RPC remote call : " + caught.getMessage(), pro.softcom.archetype.gwt.client.lib.panel.MessagePanel.Level.ERROR));

        // Delegate the "on failure" to the custom implementation 
        doOnFailure(caught);
    }

    protected abstract void doOnSuccess(T result);

    /**
     * Called when the callback ends with a failure. Can be overridden for custom behaviors. 
     *
     * @param caught The Throwable that caused the failure.
     */
    protected void doOnFailure(Throwable caught) {
        // Do nothing. Can be overridden for custom behaviors
    }

    /**
     * Hides the loading image.
     */
    private void hideLoadingImage() {
        if (loading) {
            // Hide the loading image
            eventBus.fireEvent(new LoadingEvent(false));
        }
    }
}

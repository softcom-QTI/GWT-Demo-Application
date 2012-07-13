package pro.softcom.archetype.gwt.client.base;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

public class ActivityAsyncProxy<T extends ArchetypeActivity> implements Activity {

    @Inject
    private AsyncProvider<T> provider;
    private boolean canceled = false;
    private ArchetypeActivity impl;

    @Override
    public String mayStop() {
        if (impl != null)
            return impl.mayStop();
        return null;
    }

    @Override
    public void onCancel() {
        if (impl != null) {
            impl.onCancel();
        } else {
            canceled = true;
        }
    }

    @Override
    public void onStop() {
        if (impl != null) {
            impl.onStop();
        } else {
            canceled = true;
        }
    }

    @Override
    public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
        provider.get(new AsyncCallback<T>() {
            @Override
            public void onSuccess(T result) {
                // Do not starts loaded activity if it has been canceled
                if (!canceled) {
                    impl = (ArchetypeActivity) result;
                    impl.start(panel, eventBus);
                }
            }

            @Override
            public void onFailure(Throwable caught) {
                throw new RuntimeException("Unable to load JavaScript for " + impl.getClass().getName(), caught);
            }
        });
    }
}

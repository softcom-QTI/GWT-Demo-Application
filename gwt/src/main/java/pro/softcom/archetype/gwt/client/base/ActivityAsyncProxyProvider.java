package pro.softcom.archetype.gwt.client.base;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class ActivityAsyncProxyProvider<T extends ArchetypeActivity> implements Provider<ActivityAsyncProxy<T>> {

    @Inject
    Provider<ActivityAsyncProxy<T>> provider;

    @Override
    public ActivityAsyncProxy<T> get() {
        return provider.get();
    }
}

package pro.softcom.archetype.gwt.client.base;

import pro.softcom.archetype.gwt.client.customer.edit.CustomerEditActivity;
import pro.softcom.archetype.gwt.client.customer.search.CustomerSearchActivity;
import pro.softcom.archetype.gwt.client.place.CustomerEditPlace;
import pro.softcom.archetype.gwt.client.place.CustomerSearchPlace;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.google.inject.Inject;

public class ArchetypeActivityMapper implements ActivityMapper {

    @Inject
    private ClientFactory clientFactory;

    public Activity getActivity(Place place) {
        if (place instanceof CustomerSearchPlace) {
            return new CustomerSearchActivity((CustomerSearchPlace) place, clientFactory);
        }
        if (place instanceof CustomerEditPlace) {
            return new CustomerEditActivity((CustomerEditPlace) place, clientFactory);
        }
        return null;
    }

}

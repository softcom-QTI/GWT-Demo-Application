package pro.softcom.archetype.gwt.client.base;

import pro.softcom.archetype.gwt.client.customer.edit.CustomerEditActivity;
import pro.softcom.archetype.gwt.client.customer.search.CustomerSearchActivity;
import pro.softcom.archetype.gwt.client.place.CustomerEditPlace;
import pro.softcom.archetype.gwt.client.place.CustomerSearchPlace;
import pro.softcom.archetype.gwt.client.place.SkillManagePlace;
import pro.softcom.archetype.gwt.client.skill.manage.SkillManageActivity;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.google.inject.Inject;

public class ArchetypeActivityMapper implements ActivityMapper {

    @Inject
    private ActivityAsyncProxyProvider<SkillManageActivity> skillManageActivityProvider;

    @Inject
    private ActivityAsyncProxyProvider<CustomerEditActivity> customerEditActivityProvider;

    @Inject
    private ActivityAsyncProxyProvider<CustomerSearchActivity> customerSearchActivityProvider;

    public Activity getActivity(Place place) {
        if (place instanceof SkillManagePlace) {
            return skillManageActivityProvider.get();
        }
        if (place instanceof CustomerSearchPlace) {
            return customerSearchActivityProvider.get();
        }
        if (place instanceof CustomerEditPlace) {
            return customerEditActivityProvider.get();
        }
        return null;
    }

}

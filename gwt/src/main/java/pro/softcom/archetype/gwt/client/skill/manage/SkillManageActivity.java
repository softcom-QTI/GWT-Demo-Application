package pro.softcom.archetype.gwt.client.skill.manage;

import pro.softcom.archetype.gwt.client.base.ArchetypeActivity;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

public class SkillManageActivity extends ArchetypeActivity implements SkillManageView.Presenter {

    @Inject
    private SkillManageView view;

    @Override
    public void doOnStart(AcceptsOneWidget panel) {
        panel.setWidget(view);
        view.setPresenter(this);        
    }

}

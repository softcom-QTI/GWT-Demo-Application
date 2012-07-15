package pro.softcom.archetype.gwt.client.skill.manage;

import com.google.gwt.user.client.ui.IsWidget;

public interface SkillManageView extends IsWidget {

    void setPresenter(Presenter presenter);

    void setMessage(String text);

    public interface Presenter {
    }
}

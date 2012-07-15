package pro.softcom.archetype.gwt.client.skill.manage;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class SkillManageViewImpl extends Composite implements SkillManageView {

    interface Binder extends UiBinder<Widget, SkillManageViewImpl> {
    }

    private SkillManageI18n constants = GWT.create(SkillManageI18n.class);

    private Presenter presenter;

    public SkillManageViewImpl() {
        Widget widget = GWT.<Binder> create(Binder.class).createAndBindUi(this);
        initWidget(widget);
    }

    @Override
    public Widget asWidget() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setMessage(String text) {
        // TODO Auto-generated method stub

    }

}

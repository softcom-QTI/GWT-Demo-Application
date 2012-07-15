package pro.softcom.archetype.gwt.client.skill.component;

import pro.softcom.archetype.gwt.shared.model.TopicModel;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class TopicNodeCell extends AbstractCell<TopicModel> {

    @Override
    public void render(com.google.gwt.cell.client.Cell.Context context, TopicModel value, SafeHtmlBuilder sb) {
        sb.appendEscaped(value.getName());
    }
}

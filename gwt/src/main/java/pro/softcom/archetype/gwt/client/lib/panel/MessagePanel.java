package pro.softcom.archetype.gwt.client.lib.panel;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class MessagePanel extends Composite {

    interface MessagePanelUiBinder extends UiBinder<Widget, MessagePanel> {
    }

    /**
     * The level of the message. Will mostly only have an impact on the color of the message.
     */
    public static enum Level {
        ERROR, WARN, INFO
    }

    /**
     * Represents a message, which is a text and a specific message level.
     */
    public static class Message {

        private String text;
        private Level level;

        public Message() {
            this(null, null);
        }

        public Message(String text, Level level) {
            this.text = text;
            this.level = level;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Level getLevel() {
            return level;
        }

        public void setLevel(Level level) {
            this.level = level;
        }
    }

    private static MessagePanelUiBinder uiBinder = GWT.create(MessagePanelUiBinder.class);

    public MessagePanel() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    public void addMessages(List<Message> messages) {
        // TODO
    }

}

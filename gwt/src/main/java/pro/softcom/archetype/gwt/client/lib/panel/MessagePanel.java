package pro.softcom.archetype.gwt.client.lib.panel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * This class is a component in which messages can be displayed, with specific levels (error, warn, info).
 */
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

    @UiField
    VerticalPanel messagePanel;

    public MessagePanel() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    public void addMessages(List<Message> messages) {
        Map<Level, List<Message>> messagesByLevel = new HashMap<Level, List<Message>>();

        // Sort the messages by level
        for (Message message : messages) {
            if (messagesByLevel.get(message.getLevel()) == null) {
                messagesByLevel.put(message.getLevel(), new ArrayList<Message>());
            }
            messagesByLevel.get(message.getLevel()).add(message);
        }

        // Create blocks of messages by level
        for (Entry<Level, List<Message>> entry : messagesByLevel.entrySet()) {
            formatMessages(entry.getValue()); // TODO
        }
    }

    /**
     * Format the messages as a HTML list.
     *
     * @param messages The messages to display.
     * @return The HTML String containing the messages.
     */
    private SafeHtml formatMessages(List<Message> messages) {
        SafeHtmlBuilder builder = new SafeHtmlBuilder();
        builder.appendHtmlConstant("<ul>");
        for (Message message : messages) {
            builder.appendHtmlConstant("<li>");
            builder.appendEscaped(message.getText());
            builder.appendHtmlConstant("</li>");
        }
        builder.appendHtmlConstant("</ul>");
        return builder.toSafeHtml();
    }
}

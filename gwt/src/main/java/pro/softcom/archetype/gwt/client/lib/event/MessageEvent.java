package pro.softcom.archetype.gwt.client.lib.event;

import java.util.ArrayList;

import pro.softcom.archetype.gwt.client.lib.panel.MessagePanel.Level;
import pro.softcom.archetype.gwt.client.lib.panel.MessagePanel.Message;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

public class MessageEvent extends GwtEvent<MessageEvent.Handler> {

    private final ArrayList<Message> messages = new ArrayList<Message>();

    public void addMessage(String text, Level level) {
        messages.add(new Message(text, level));
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public void addMessages(ArrayList<Message> message) {
        messages.addAll(message);
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public interface Handler extends EventHandler {
        void onMessageEvent(MessageEvent event);
    }

    private static final Type<Handler> TYPE = new Type<Handler>();

    public static HandlerRegistration register(EventBus eventBus, MessageEvent.Handler handler) {
        return eventBus.addHandler(TYPE, handler);
    }

    @Override
    public GwtEvent.Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(Handler handler) {
        handler.onMessageEvent(this);
    }

}

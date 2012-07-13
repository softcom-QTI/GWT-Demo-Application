package pro.softcom.archetype.gwt.client.lib.panel;

import pro.softcom.archetype.gwt.client.util.StringUtil;

import com.google.gwt.event.dom.client.HasAllMouseHandlers;
import com.google.gwt.event.dom.client.HasMouseMoveHandlers;
import com.google.gwt.event.dom.client.HasMouseOutHandlers;
import com.google.gwt.event.dom.client.HasMouseOverHandlers;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is a class for custom tooltips. It allows single/multiline text tooltips, as well as completely custom widgets tooltips (images, tables, etc.)
 * </br></br>
 * <b>Simple example :</b></br>
 * <pre>
 * &lt;p:Tooltip multiline="This is a multiline\ntooltip !" &gt;
 *   &lt;g:Image url="images/help.png" /&gt;
 * &lt;/p:Tooltip&gt;
 * </pre>
 * This will add a multiline tooltip on the help.png image.
 */
public class Tooltip extends SimplePanel {

    private PopupPanel popup;

    /**
     * This offset is used to place the popup panel just a little bit near the cursor,
     * to avoid the blinking effect if it were placed directly under the cursor.
     */
    private static final int MOUSE_OFFSET = 1;

    public Tooltip() {
        popup = new PopupPanel();
    }

    /**
     * Sets the given Widget as the content of the popup panel that will be displayed when the mouse is over the "tooltipped" object.
     *
     * @param widget
     */
    public void setTooltip(Widget widget) {
        popup.setWidget(widget);
    }

    /**
     * Convenience method to set only a simple text as tooltip.
     *
     * @param text The tooltip text.
     */
    public void setText(String text) {
        // Only add the widget when the given is not null nor empty
        if (!StringUtil.isNullOrEmpty(text)) {
            setTooltip(new Label(text));
        } else {
            setTooltip(null);
        }
    }

    /**
     * Convenience method to set a multiline text as tooltip.
     * The line breaks (\n) in the given text will be replaced by <br/> tags.
     *
     * @param text The multiline tooltip text.
     */
    public void setMultiline(String text) {
        // Only add the widget when the given is not null nor empty
        if (!StringUtil.isNullOrEmpty(text)) {
            setTooltip(new HTML(StringUtil.replaceLineBreaks(text)));
        } else {
            setTooltip(null);
        }
    }

    @Override
    public void setWidget(Widget widget) {
        if (widget != null) {
            // Only add the mouse handlers on widgets that can handle them
            if (widget instanceof HasAllMouseHandlers) {
                addHandlers(widget);
            }
        }

        super.setWidget(widget);
    }

    private void addHandlers(final Widget widget) {
        ((HasMouseOverHandlers) widget).addMouseOverHandler(new MouseOverHandler() {
            @Override
            public void onMouseOver(MouseOverEvent event) {
                // Only show the tooltip if it is available
                if (isTooltipAvailable()) {
                    popup.setPopupPosition(event.getClientX() + MOUSE_OFFSET, event.getClientY() - MOUSE_OFFSET - popup.getOffsetHeight());
                    popup.show();

                    // The cursor will be "help" when the mouse is over the widget 
                    widget.addStyleName("tooltip-cursor");
                }
            }
        });

        // With this handler, the tooltip will follow the cursor
        ((HasMouseMoveHandlers) widget).addMouseMoveHandler(new MouseMoveHandler() {
            @Override
            public void onMouseMove(MouseMoveEvent event) {
                // Only move the tooltip if it is available
                if (isTooltipAvailable()) {
                    popup.setPopupPosition(event.getClientX() + MOUSE_OFFSET, event.getClientY() - MOUSE_OFFSET - popup.getOffsetHeight());
                }
            }
        });

        ((HasMouseOutHandlers) widget).addMouseOutHandler(new MouseOutHandler() {
            @Override
            public void onMouseOut(MouseOutEvent event) {
                // Only hide the tooltip if it is available
                if (isTooltipAvailable()) {
                    popup.hide();

                    // Remove the style after mousing out of the widget
                    widget.removeStyleName("tooltip-cursor");
                }
            }
        });
    }

    /**
     * Checks if a widget has been set as tooltip.
     *
     * @return True if a widget has been set as tooltip.
     */
    private boolean isTooltipAvailable() {
        return popup.getWidget() != null;
    }

}

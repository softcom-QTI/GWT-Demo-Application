package pro.softcom.archetype.gwt.client.lib.menu;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;

public class SoftcomMenuItem extends MenuItem {
	/**
	 * The menu item icon.
	 */
	private Element icon;

	/**
	 * The menu item label.
	 */
	private HTML label;

	/**
	 * The text used to display the shortcut key to the user.
	 */
	private Label shortcutKeyLabel;

	/**
	 * Constructs a new menu item that fires a command when it is selected.
	 * 
	 * @param text
	 *            the item's text
	 * @param cmd
	 *            the command to be fired when it is selected
	 */
	public SoftcomMenuItem(String text, Command cmd) {
		super(text, cmd);
	}

	/**
	 * Constructs a new menu item that fires a command when it is selected.
	 * 
	 * @param text
	 *            the item's text
	 * @param asHTML
	 *            <code>true</code> to treat the specified text as html
	 * @param cmd
	 *            the command to be fired when it is selected
	 */
	public SoftcomMenuItem(String text, boolean asHTML, Command cmd) {
		super(text, asHTML, cmd);
	}

	/**
	 * Constructs a new menu item that cascades to a sub-menu when it is
	 * selected.
	 * 
	 * @param text
	 *            the item's text
	 * @param subMenu
	 *            the sub-menu to be displayed when it is selected
	 */
	public SoftcomMenuItem(String text, MenuBar subMenu) {
		super(text, subMenu);
	}

	/**
	 * Constructs a new menu item that cascades to a sub-menu when it is
	 * selected.
	 * 
	 * @param text
	 *            the item's text
	 * @param asHTML
	 *            <code>true</code> to treat the specified text as html
	 * @param subMenu
	 *            the sub-menu to be displayed when it is selected
	 */
	public SoftcomMenuItem(String text, boolean asHTML, MenuBar subMenu) {
		super(text, asHTML, subMenu);
	}

	/**
	 * Sets the icon of this menu item using the element created from an
	 * AbstractImagePrototype.
	 * 
	 * @param icon
	 *            the icon to be displayed on the left of this menu item
	 */
	public void setIcon(ImageResource icon) {
		setIcon((Element) AbstractImagePrototype.create(icon).createElement().cast());
	}

	/**
	 * Sets the icon of this menu item.
	 * 
	 * @param icon
	 *            the icon to be displayed on the left of this menu item
	 */
	public void setIcon(Element icon) {
		if (this.icon != null) {
			this.icon.removeFromParent();
		}
		this.icon = icon;
		icon.setClassName("gwt-MenuItemIcon");
		getElement().insertBefore(icon, label.getElement());
	}

	/**
	 * @return the icon of this menu item
	 */
	public Element getIcon() {
		return icon;
	}

	/**
	 * Creates the label of this menu item if it wasn't created already.
	 */
	private void maybeCreateLabel() {
		if (label == null) {
			label = new HTML();
			label.setStyleName("gwt-MenuItemLabel");
			getElement().appendChild(label.getElement());
		}
	}

	@Override
	public void setHTML(String html) {
		maybeCreateLabel();
		label.setHTML(html);
	}

	@Override
	public String getHTML() {
		return label != null ? label.getHTML() : null;
	}

	@Override
	public void setText(String text) {
		maybeCreateLabel();
		label.setText(text);
	}

	@Override
	public String getText() {
		return label != null ? label.getText() : null;
	}

	/**
	 * Creates the shortcut key label for this menu item if it wasn't created
	 * already.
	 */
	private void maybeCreateShortcutKeyLabel() {
		if (shortcutKeyLabel == null) {
			shortcutKeyLabel = new Label();
			shortcutKeyLabel.setStyleName("gwt-MenuItemShortcutKeyLabel");
			getElement().insertAfter(shortcutKeyLabel.getElement(), label.getElement());
		}
	}

	/**
	 * Sets the text representing the shortcut key associated with this menu
	 * item.
	 * 
	 * @param text
	 *            the text used to display the shortcut key
	 */
	public void setShortcutKeyLabel(String text) {
		maybeCreateShortcutKeyLabel();
		shortcutKeyLabel.setText(text);
	}

	/**
	 * @return the text representing the shortcut key associated with this menu
	 *         item, {@code null} if the shortcut key label hasn't been set
	 */
	public String getShortcutKeyLabel() {
		return shortcutKeyLabel != null ? shortcutKeyLabel.getText() : null;
	}
}

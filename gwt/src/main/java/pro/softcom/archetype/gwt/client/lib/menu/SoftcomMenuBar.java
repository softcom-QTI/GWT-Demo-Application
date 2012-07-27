package pro.softcom.archetype.gwt.client.lib.menu;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;

/**
 * Gwt MenuBar mit Jfa Integration. MenuItens werden dazu mit Id-String der
 * benoetigten JFA-Feinauthorisierung hinzugefuegt und erscheinen nur wenn der
 * aktuelle eingeloggte Benutzer eine entsprechende Berechtigung hat.
 */
public class SoftcomMenuBar extends MenuBar {
	/**
	 * Creates an empty horizontal menu bar.
	 */
	public SoftcomMenuBar() {
		super();
	}

	/**
	 * Creates an empty menu bar.
	 * 
	 * @param vertical
	 *            <code>true</code> to orient the menu bar vertically
	 */
	public SoftcomMenuBar(boolean vertical) {
		super(vertical);
	}

	/**
	 * Creates an empty menu bar that uses the specified ClientBundle for menu
	 * images.
	 * 
	 * @param vertical
	 *            <code>true</code> to orient the menu bar vertically
	 * @param resources
	 *            a bundle that provides images for this menu
	 */
	public SoftcomMenuBar(boolean vertical, Resources resources) {
		super(vertical, resources);
	}

	/**
	 * Creates an empty horizontal menu bar that uses the specified ClientBundle
	 * for menu images.
	 * 
	 * @param resources
	 *            a bundle that provides images for this menu
	 */
	public SoftcomMenuBar(Resources resources) {
		super(resources);
	}

	  /**
	   * Adds a menu item to the bar.
	   *
	   * @param item the item to be added
	   * @return the {@link MenuItem} object
	   */
	  public SoftcomMenuItem addItem(SoftcomMenuItem item) {
		MenuItem mi = super.addItem(item);
		SoftcomMenuItem smi = new SoftcomMenuItem(mi.getText(), mi.getCommand());
	    return smi;
	  }

	/**
	 * Adds a menu item to the bar.
	 * 
	 * @param entry
	 *            the item to be added
	 * @param icon 
	 * @return the {@link IpasMenuItem} object
	 */
	public SoftcomMenuItem addItem(MenuItem entry, String shortcut, ImageResource icon) {
		SoftcomMenuItem ipasItem = new SoftcomMenuItem(entry.getText(), entry.getCommand());
		if (shortcut != null && !shortcut.equals("")) {
			ipasItem.setShortcutKeyLabel(shortcut);
		}
		if (icon != null) {
			ipasItem.setIcon(icon);
		}
		super.addItem(ipasItem);
		return ipasItem;
	}

	  /**
	   * Adds a menu item to the bar, that will open the specified menu when it is
	   * selected.
	   *
	   * @param text the item's text
	   * @param popup the menu to be cascaded from it
	   * @return the {@link MenuItem} object created
	   */
	  public MenuItem addItem(String text, SoftcomMenuBar popup) {
	    return super.addItem(text, popup);
	  }

	/**
	 * Adds a menu item to the bar at a specific index.
	 * 
	 * @param entry
	 *            the item to be inserted
	 * @param beforeIndex
	 *            the index where the item should be inserted
	 * @return the {@link MenuItem} object
	 * @throws IndexOutOfBoundsException
	 *             if <code>beforeIndex</code> is out of range
	 */
	public MenuItem insertItem(MenuItem entry, int beforeIndex) {
		MenuItem menuItem = new MenuItem(entry.getText(), entry.getCommand());
		MenuItem item = super.insertItem(menuItem, beforeIndex);
		return item;
	}
}

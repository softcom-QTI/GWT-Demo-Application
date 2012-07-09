package pro.softcom.archetype.gwt.client.lib.table;

import java.util.Comparator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.CellTable.Resources;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.Range;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class ArchetypeCellTable<T> extends Composite {

	interface Binder extends UiBinder<Widget, ArchetypeCellTable<?>> {
	}

	private final ListDataProvider<T> dataProvider = new ListDataProvider<T>();

	private final SingleSelectionModel<T> selectionModel = new SingleSelectionModel<T>();

	private final ListHandler<T> sortHandler = new ListHandler<T>(dataProvider.getList());

	@UiField
	VerticalPanel layout;

	@UiField(provided = true)
	CellTable<T> cellTable;

	private SimplePager pager = new SimplePager(SimplePager.TextLocation.CENTER, (SimplePager.Resources) GWT.create(SimplePager.Resources.class), false, 0, true) {
		/**
		 * {@inheritDoc}
		 * 
		 * This method is overridden to prevent the 1-10 9-18 notation when
		 * having 18 elements and page size 10, for example. It will now display
		 * 1-10, 11-18, and display the rest as empty lines.
		 */
		@Override
		public void setPageStart(int index) {
			if (getDisplay() != null) {
				Range range = getDisplay().getVisibleRange();
				int pageSize = range.getLength();

				// Removed the min to show fixed ranges
				// if (isRangeLimited && display.isRowCountExact()) {
				// index = Math.min(index, display.getRowCount() - pageSize);
				// }

				index = Math.max(0, index);
				if (index != range.getStart()) {
					getDisplay().setVisibleRange(index, pageSize);
				}
			}
		}
	};
	private boolean pagerEnabled;
	private boolean selectionEnabled;

	private static final int DEFAULT_PAGESIZE = 15;

	/**
	 * The page size that was originally set to the table.
	 */
	private final int pageSize;

	private static final CellTableResource resources = GWT.create(CellTableResource.class);

	// Multiple convenience constructors..
	public ArchetypeCellTable() {
		this(DEFAULT_PAGESIZE, resources, null, null);
	}

	public ArchetypeCellTable(int pageSize) {
		this(pageSize, resources, null, null);
	}

	public ArchetypeCellTable(int pageSize, ProvidesKey<T> keyProvider) {
		this(pageSize, resources, keyProvider, null);
	}

	public ArchetypeCellTable(int pageSize, Resources resources, ProvidesKey<T> keyProvider) {
		this(pageSize, resources, keyProvider, null);
	}

	public ArchetypeCellTable(int pageSize, Resources resources, ProvidesKey<T> keyProvider, Widget loadingIndicator) {
		this.pageSize = pageSize;

		// Create the cell table
		cellTable = new CellTable<T>(pageSize, resources, keyProvider, loadingIndicator);

		// Initialize the UI
		Widget widget = GWT.<Binder> create(Binder.class).createAndBindUi(this);
		initWidget(widget);

		// Link the sort handler to the table
		cellTable.addColumnSortHandler(sortHandler);

		// Link the data provider to the table
		dataProvider.addDataDisplay(cellTable);

		// Selection is enabled by default
		setSelectionEnabled(true);

		// The pager is disabled by default
		setPagerEnabled(false);
	}

	/**
	 * Adds a column to the end of the table.
	 * 
	 * @param col
	 *            The column to be added.
	 */
	public void addColumn(Column<T, ?> col) {
		addColumn(col, null, null);
	}

	/**
	 * Adds a column to the end of the table. The column will be sortable, using
	 * the given comparator.
	 * 
	 * @param col
	 *            The column to be added.
	 * @param comparator
	 *            The comparator used to sort the column.
	 */
	public void addColumn(Column<T, ?> col, Comparator<T> comparator) {
		addColumn(col, null, comparator);
	}

	/**
	 * Adds a column to the end of the table with an associated String header.
	 * 
	 * @param col
	 *            The column to be added.
	 * @param headerString
	 *            The associated header text, as a String.
	 */
	public void addColumn(Column<T, ?> col, String headerString) {
		addColumn(col, headerString, null);
	}

	/**
	 * Adds a column to the end of the table with an associated String header.
	 * The column will be sortable, using the given comparator.
	 * 
	 * @param col
	 *            The column to be added.
	 * @param headerString
	 *            The associated header text, as a String.
	 * @param comparator
	 *            The comparator used to sort the column.
	 */
	public void addColumn(Column<T, ?> col, String headerString, Comparator<T> comparator) {
		// Set the column as sortable
		if (comparator != null) {
			col.setSortable(true);
			sortHandler.setComparator(col, comparator);
		}

		cellTable.addColumn(col, headerString);
	}

	/**
	 * Set the width of a {@link Column}.
	 * 
	 * <p>
	 * The layout behavior depends on whether or not the table is using fixed
	 * layout.
	 * </p>
	 * 
	 * @param column
	 *            the column.
	 * @param width
	 *            the width of the column.
	 * 
	 * @see {@link CellTable#setTableLayoutFixed(boolean)}
	 */
	public void setColumnWidth(Column<T, ?> column, String width) {
		cellTable.setColumnWidth(column, width);
	}

	/**
	 * Adds a {@link SelectionChangeEvent} handler.
	 * 
	 * @param handler
	 *            the handler.
	 * @return {@link HandlerRegistration} used to remove this handler.
	 */
	public HandlerRegistration addSelectionChangeHandler(SelectionChangeEvent.Handler handler) {
		return selectionModel.addSelectionChangeHandler(handler);
	}

	/**
	 * Check if an object is selected.
	 * 
	 * @param object
	 *            the object
	 * @return true if selected, false if not
	 */
	public boolean isSelected(T object) {
		return selectionModel.isSelected(object);
	}

	/**
	 * Set the selected state of an object and fire a
	 * {@link SelectionChangeEvent} if the selection has changed. Subclasses
	 * should not fire an event in the case where selected is true and the
	 * object was already selected, or selected is false and the object was not
	 * previously selected.
	 * 
	 * @param object
	 *            the object to select or deselect
	 * @param selected
	 *            true to select, false to deselect
	 */
	public void setSelected(T object, boolean selected) {
		selectionModel.setSelected(object, selected);
	}

	/**
	 * Return the currently selected object (can be null).
	 * 
	 * @return The selected object.
	 */
	public T getSelectedObject() {
		return selectionModel.getSelectedObject();
	}

	/**
	 * Enable/disable the selection on the list.
	 * 
	 * @param enabled
	 *            True to enable, false to disable.
	 */
	public void setSelectionEnabled(boolean enabled) {
		selectionEnabled = enabled;

		if (selectionEnabled) {
			cellTable.setSelectionModel(selectionModel);
		} else {
			cellTable.setSelectionModel(null);
		}
	}

	/**
	 * Set the new list to display.
	 * 
	 * @param list
	 *            The list to display.
	 */
	public void setList(List<T> list) {
		// Don't set a new list, just use the same one and replace the content
		// (setting a new list would break the sorting mechanism)
		dataProvider.getList().clear();
		dataProvider.getList().addAll(list);

		refreshPageSize();
	}

	/**
	 * Return the list of displayed elements.
	 * 
	 * @return The list of displayed elements.
	 */
	public List<T> getList() {
		return dataProvider.getList();
	}

	/**
	 * Clear the displayed list.
	 */
	public void clear() {
		dataProvider.getList().clear();
	}

	/**
	 * Add the given element to the displayed list.
	 */
	public void add(T element) {
		// Add the given element
		dataProvider.getList().add(element);

		refreshPageSize();
	}

	/**
	 * Remove the given element from the displayed list.
	 * 
	 * @param element
	 *            The element to remove.
	 */
	public void remove(T element) {
		dataProvider.getList().remove(element);
	}

	/**
	 * Refresh the displayed list.
	 */
	public void refresh() {
		dataProvider.refresh();
	}

	/**
	 * Enable/disable the pager. If the pager is disabled, all elements will be
	 * displayed on the same page.
	 * 
	 * @param enabled
	 *            True to enable, false to disable.
	 */
	public void setPagerEnabled(boolean enabled) {
		pagerEnabled = enabled;

		refreshPageSize();
	}

	private void refreshPageSize() {
		if (pagerEnabled) {
			layout.add(pager);
			pager.setDisplay(cellTable);
			cellTable.setPageSize(pageSize);
		} else {
			layout.remove(pager);
			pager.setDisplay(null);
			// Adjust the size to the whole list when there is no pager
			cellTable.setPageSize(dataProvider.getList().size());
		}
	}
}

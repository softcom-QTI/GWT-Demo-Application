package pro.softcom.archetype.gwt.client.lib.table;

import com.google.gwt.user.cellview.client.CellTable;

public interface CellTableResource extends CellTable.Resources {
	public interface CellTableStyle extends CellTable.Style {
	};

	@Source({ CellTable.Style.DEFAULT_CSS, "cellTable.css" })
	CellTableStyle cellTableStyle();
}

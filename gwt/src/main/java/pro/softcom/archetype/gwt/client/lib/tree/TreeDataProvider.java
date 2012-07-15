package pro.softcom.archetype.gwt.client.lib.tree;

import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.view.client.TreeViewModel;

/**
 * Interface to be implemented to create an object responsible for getting data to be displayed 
 * in a {@link CellTree}.
 * 
 * This interface is typically implemented by an anonymous class in an Activity and passed to a 
 * {@link TreeViewModel} implementation.
 */
public interface TreeDataProvider<T> {

    public void getRootElements(TreeDataCallback<T> callback);

    public void getChildElements(Object parentId, TreeDataCallback<T> callback);
}

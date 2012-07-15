package pro.softcom.archetype.gwt.client.lib.tree;

import java.util.List;

/**
 * Callback interface used by {@link TreeDataProvider}
 */
public interface TreeDataCallback<T> {

    public void dataReady(List<T> nodeList);
}

package pro.softcom.archetype.gwt.client.skill.component;

import pro.softcom.archetype.gwt.client.lib.tree.TreeDataProvider;
import pro.softcom.archetype.gwt.shared.model.TopicModel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.NoSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;

/**
 * Widget displaying a tree of topics.
 */
public class TopicTree extends Composite implements HasSelectionHandlers<TopicModel> {

    interface Binder extends UiBinder<Widget, TopicTree> {
    }

    @UiField(provided = true)
    CellTree cellTree;

    private TopicTreeViewModel treeModel;

    private NoSelectionModel<TopicModel> selectionModel = new NoSelectionModel<TopicModel>();

    /** The registry currently selected in the tree */
    private TopicModel selectedRegistry;

    public TopicTree() {

        // Init registry tree stuff
        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {

            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                selectedRegistry = selectionModel.getLastSelectedObject();
                if (selectedRegistry != null) {
                    fireSelectionEvent(selectedRegistry);
                }
            }
        });

        treeModel = new TopicTreeViewModel(selectionModel);
        cellTree = new CellTree(treeModel, null);

        Widget widget = GWT.<Binder> create(Binder.class).createAndBindUi(this);
        initWidget(widget);

        setStyleName("barbara-RegistryTree");
    }

    public void setDataProvider(TreeDataProvider<TopicModel> treeDataProvider) {
        treeModel.setDataProvider(treeDataProvider);
    }

    public TopicModel getSelectedRegistry() {
        return selectedRegistry;
    }

    /**
     * Refresh the tree content for the node given as parameter
     */
    public void refresh(TopicModel node) {
        treeModel.refresh(node);
    }

    private void fireSelectionEvent(TopicModel selectedRegistry) {
        SelectionEvent.fire(this, selectedRegistry);
    }

    // Methods from interface HasSelectionHandlers

    @Override
    public HandlerRegistration addSelectionHandler(SelectionHandler<TopicModel> handler) {
        return addHandler(handler, SelectionEvent.getType());
    }

}

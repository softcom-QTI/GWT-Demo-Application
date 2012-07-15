package pro.softcom.archetype.gwt.client.skill.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pro.softcom.archetype.gwt.client.lib.tree.TreeDataCallback;
import pro.softcom.archetype.gwt.client.lib.tree.TreeDataProvider;
import pro.softcom.archetype.gwt.shared.model.TopicModel;

import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.TreeViewModel;

public class TopicTreeViewModel implements TreeViewModel {

    private static final ProvidesKey<TopicModel> KEY_PROVIDER = new ProvidesKey<TopicModel>() {
        public Object getKey(TopicModel item) {
            
            return item == null ? null : item.getId();
        }
    };

    private SelectionModel<TopicModel> selectionModel;
    
    // TODO decorated cell
    private final TopicNodeCell registryNodeCell;
    
    /** Provider used to query data for root and child elements */
    private TreeDataProvider<TopicModel> treeDataProvider;
    
    /** ListDataProvider for the root elements of the tree */
    private ListDataProvider<TopicModel> rootListDataProvider;

    /** Data providers by registry DTO, used to handle refresh of a given node */
    private Map<TopicModel, ListDataProvider<TopicModel>> dataProviders = new HashMap<TopicModel, ListDataProvider<TopicModel>>();
    
    public TopicTreeViewModel(SelectionModel<TopicModel> selectionModel) {
        this.selectionModel = selectionModel;
        registryNodeCell = new TopicNodeCell();
    }

    public void setDataProvider(TreeDataProvider<TopicModel> treeDataProvider) {
        this.treeDataProvider = treeDataProvider;
        loadRootElements();
    }

    private void loadRootElements() {
        TreeDataCallback<TopicModel> callback = new TreeDataCallback<TopicModel>() {

            @Override
            public void dataReady(List<TopicModel> nodeList) {
                rootListDataProvider.getList().clear();
                rootListDataProvider.getList().addAll(nodeList);
                rootListDataProvider.refresh();
            }
        };
        treeDataProvider.getRootElements(callback);
    }

    @Override
    public <T> NodeInfo<?> getNodeInfo(T value) {

        final ListDataProvider<TopicModel> listDataProvider = new ListDataProvider<TopicModel>(new ArrayList<TopicModel>(), KEY_PROVIDER);
        if (value == null) {

            // Root elements, do not use treeDataProvider because it is not initialized yet
            rootListDataProvider = listDataProvider;

        } else if (value instanceof TopicModel) {

            // Childrens
            TopicModel node = (TopicModel) value;
            TreeDataCallback<TopicModel> callback = new TreeDataCallback<TopicModel>() {

                @Override
                public void dataReady(List<TopicModel> nodeList) {
                    listDataProvider.getList().clear();
                    listDataProvider.getList().addAll(nodeList);
                    listDataProvider.refresh();
                }
            };
            treeDataProvider.getChildElements(node.getId(), callback);
            dataProviders.put(node, listDataProvider);

        } else {

            throw new IllegalArgumentException("Unsupported object type: " + value.getClass().getName());
        }

        return new DefaultNodeInfo<TopicModel>(listDataProvider, registryNodeCell, selectionModel, null);
    }

    @Override
    public boolean isLeaf(Object value) {
        if (value == null) {

            return false;
        }
        if (!(value instanceof TopicModel)) {
            String type = value.getClass().getName();

            throw new IllegalArgumentException("Unsupported object type: " + type);
        }
        TopicModel dto = (TopicModel) value;
        if (dto.getChildTopics().size() > 0) {

            return false;
        }

        return true;
    }

    /**
     * Refresh the tree model for the node given as parameter
     */
    public void refresh(TopicModel node) {
        ListDataProvider<TopicModel> listDataProviderToRefresh = null;
        if (node == null) {
            listDataProviderToRefresh = rootListDataProvider;
        } else {
            listDataProviderToRefresh = dataProviders.get(node);
        }
        
        if (listDataProviderToRefresh != null) {
            final ListDataProvider<TopicModel> ldp = listDataProviderToRefresh;
            TreeDataCallback<TopicModel> callback = new TreeDataCallback<TopicModel>() {

                @Override
                public void dataReady(List<TopicModel> nodeList) {
                    ldp.getList().clear();
                    ldp.getList().addAll(nodeList);
                    ldp.refresh();
                }
            };
            treeDataProvider.getChildElements(node.getId(), callback);
        }
    }
}

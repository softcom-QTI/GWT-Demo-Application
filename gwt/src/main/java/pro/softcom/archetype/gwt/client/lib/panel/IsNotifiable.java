package pro.softcom.archetype.gwt.client.lib.panel;

public interface IsNotifiable<T> {

    /**
     * This method must be called by the presenter after a creation success.
     * 
     * @param result
     *            The result of the create operation.
     */
    void afterNewSuccess(T result);

    /**
     * This method must be called by the presenter after a creation failure.
     */
    void afterNewFailure();

    /**
     * This method must be called by the presenter after an update success.
     * 
     * @param result
     *            The result of the modify operation.
     */
    void afterSaveSuccess(T result);

    /**
     * This method must be called by the presenter after an update failure.
     */
    void afterSaveFailure();

    /**
     * This method must be called by the presenter after a deletion success.
     */
    void afterDeleteSuccess();

    /**
     * This method must be called by the presenter after a deletion failure.
     */
    void afterDeleteFailure();
}

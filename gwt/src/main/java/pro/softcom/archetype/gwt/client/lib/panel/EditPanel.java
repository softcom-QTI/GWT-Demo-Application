package pro.softcom.archetype.gwt.client.lib.panel;

import java.util.Iterator;

import pro.softcom.archetype.gwt.client.lib.event.DeleteEvent;
import pro.softcom.archetype.gwt.client.lib.event.DeleteHandler;
import pro.softcom.archetype.gwt.client.lib.event.SaveEvent;
import pro.softcom.archetype.gwt.client.lib.event.SaveHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

/**
 * Component used for the edition of objects. Contains the standard buttons
 * mechanisms.
 */
public class EditPanel<T> extends Composite implements HasWidgets, IsNotifiable<T> {

	// Possibles states of the edit panel
	public enum State {
		/**
		 * When an object is being edited. Fields are enabled, and buttons
		 * "Save" and "Cancel" can be clicked.
		 */
		EDITION,
		/**
		 * When no object is set to the panel. Fields are disabled, and only the
		 * "New" button can be clicked.
		 */
		BLANK,
		/**
		 * When an object is set to the panel. Fields are disabled, and buttons
		 * "New", "Edit" and "Delete" can be clicked.
		 */
		EDITABLE,
		/**
		 * When a new object is being edited. Fields are enabled, and buttons
		 * "Save" and "Cancel" can be clicked.
		 */
		NEW
	};

	// Possible combinations of buttons that can be displayed
	public enum ButtonsMode {
		WITH_ALL, WITHOUT_NEW, WITHOUT_DELETE, WITHOUT_NEW_NOR_DELETE
	};

	interface EditPanelUiBinder extends UiBinder<Widget, EditPanel<?>> {
	};

	/**
	 * Interface describing the methods that the class using the EditPanel must
	 * provide, using the setFieldsPanel(FieldsPanel<T> fieldsPanel) method.
	 */
	public interface FieldsPanel<T> {
		/**
		 * Enable/disable the fields used to display the object attributes.
		 * 
		 * @param enabled
		 */
		void setFieldsEnabled(boolean enabled);

		/**
		 * Display the attributes of the given object.
		 * 
		 * @param editedObject
		 *            The object to display.
		 */
		void displayEditedObject(T editedObject);

		/**
		 * Update the attributes of the given object and return the updated
		 * object.
		 * 
		 * @param editedObject
		 *            The object to update.
		 * @return The object with the attributes updated.
		 */
		T updateEditedObject(T editedObject);
	};

	/**
	 * This is a reference to the edited object, and will be used if the user
	 * cancels the edition of the object or in case of callback failures.
	 */
	private T editedObject;

	/**
	 * The current buttons mode of the edit panel.
	 */
	private ButtonsMode buttonsMode;

	/**
	 * The current state of the edit panel.
	 */
	private State state;

	/**
	 * A reference to a panel capable of displaying the edited object.
	 */
	private FieldsPanel<T> fieldsPanel;

	/**
	 * The handler to call on a click on the save button.
	 */
	private SaveHandler<T> saveHandler;

	/**
	 * The handler to call on a click on the delete button.
	 */
	private DeleteHandler<T> deleteHandler;

	/**
	 * Option to enable/disable the confirmation popup when deleting the object.
	 */
	private boolean deleteConfirmationRequired;

	@UiField
	Button newButton;

	@UiField
	Button editButton;

	@UiField
	Button saveButton;

	@UiField
	Button deleteButton;

	@UiField
	Button cancelButton;

	@UiField
	FlowPanel fieldsPanelContainer;

	public EditPanel() {
		// Create the components and the view
		EditPanelUiBinder uiBinder = GWT.create(EditPanelUiBinder.class);
		initWidget(uiBinder.createAndBindUi(this));

		// Set the default buttons mode
		setButtonsMode(ButtonsMode.WITH_ALL);

		// Set the default state
		setState(State.BLANK);

		// A delete confirmation is required by default
		setDeleteConfirmationRequired(true);
	}

	/**
	 * Sets the buttons mode of the EditPanel. This will enable/disable the new
	 * and/or delete buttons.
	 * 
	 * @param buttonsMode
	 *            The new buttons mode
	 */
	public void setButtonsMode(ButtonsMode buttonsMode) {
		this.buttonsMode = buttonsMode;

		switch (this.buttonsMode) {
		default:
		case WITH_ALL:
			// Set the new and delete buttons visible
			newButton.setVisible(true);
			deleteButton.setVisible(true);
			break;
		case WITHOUT_NEW:
			// Set only the delete button visible
			newButton.setVisible(false);
			deleteButton.setVisible(true);
			break;
		case WITHOUT_DELETE:
			// Set only the new button visible
			newButton.setVisible(true);
			deleteButton.setVisible(false);
			break;
		case WITHOUT_NEW_NOR_DELETE:
			// Set no button visible
			newButton.setVisible(false);
			deleteButton.setVisible(false);
			break;
		}
	}

	/**
	 * Returns the current state of the EditPanel.
	 * 
	 * @return The current state of the EditPanel.
	 */
	public State getState() {
		return state;
	}

	/**
	 * Changes the state of the edit panel. This will change the visibility
	 * and/or enabled attribute of the buttons.
	 * 
	 * @param state
	 *            The new state of the edit panel.
	 */
	public void setState(State state) {
		this.state = state;

		switch (this.state) {
		case EDITABLE:
			// Enable / disable the correct buttons
			newButton.setEnabled(true);
			editButton.setEnabled(true);
			editButton.setVisible(true);
			saveButton.setEnabled(false);
			saveButton.setVisible(false);
			deleteButton.setEnabled(true);
			cancelButton.setEnabled(false);

			// Disable the inputs fields
			if (fieldsPanel != null) {
				fieldsPanel.setFieldsEnabled(false);
			}
			break;
		case NEW:
		case EDITION:
			// Enable / disable the correct buttons
			newButton.setEnabled(false);
			editButton.setEnabled(false);
			editButton.setVisible(false);
			saveButton.setEnabled(true);
			saveButton.setVisible(true);
			deleteButton.setEnabled(false);
			cancelButton.setEnabled(true);

			// Enable the inputs fields
			if (fieldsPanel != null) {
				fieldsPanel.setFieldsEnabled(true);
			}
			break;
		case BLANK:
		default:
			// Enable / disable the correct buttons
			newButton.setEnabled(true);
			editButton.setEnabled(false);
			editButton.setVisible(true);
			saveButton.setEnabled(false);
			saveButton.setVisible(false);
			deleteButton.setEnabled(false);
			cancelButton.setEnabled(false);

			// Disable the inputs fields
			if (fieldsPanel != null) {
				fieldsPanel.setFieldsEnabled(false);
			}
			break;
		}
	}

	/**
	 * Return the edited object with its attributes updated with the values from
	 * the fields panel.
	 * 
	 * @return The updated edited object.
	 */
	public T getEditedObject() {
		return fieldsPanel != null ? fieldsPanel.updateEditedObject(editedObject) : null;
	}

	/**
	 * Set an object to the EditPanel. If the given object is not null, this
	 * will switch the EditPanel state to EDITABLE. The given object will also
	 * be displayed in the FieldsPanel.
	 * 
	 * @param editedObject
	 *            The object that will be edited.
	 */
	public void setEditedObject(T editedObject) {
		this.editedObject = editedObject;
		if (this.editedObject != null) {
			setState(State.EDITABLE);
		}
		if (fieldsPanel != null) {
			fieldsPanel.displayEditedObject(editedObject);
		}
	}

	/**
	 * Returns true if a confirmation is required when deleting an object (a
	 * popup will ask the user).
	 * 
	 * @return True if the confirmation is required, else false.
	 */
	public boolean isDeleteConfirmationRequired() {
		return deleteConfirmationRequired;
	}

	/**
	 * Set whether the delete confirmation is required or not.
	 * 
	 * @param deleteConfirmationRequired
	 *            The new value.
	 */
	public void setDeleteConfirmationRequired(boolean deleteConfirmationRequired) {
		this.deleteConfirmationRequired = deleteConfirmationRequired;
	}

	/**
	 * Set the reference to the panel capable of displaying the content of the
	 * generic attribute class.
	 * 
	 * @param fieldsPanel
	 */
	public void setFieldsPanel(FieldsPanel<T> fieldsPanel) {
		this.fieldsPanel = fieldsPanel;
		// Refresh the state
		setState(getState());
	}

	public void afterNewSuccess(T result) {
		// The edited object becomes the result of the create operation
		setEditedObject(result);
		// Set the state to EDITABLE
		setState(State.EDITABLE);
	}

	public void afterNewFailure() {
		// Set the edited object in the display
		setEditedObject(editedObject);
		// Set the state to NEW
		setState(State.NEW);
	}

	public void afterSaveSuccess(T result) {
		// The edited object becomes the result of the modify operation
		setEditedObject(result);
		// Set the state to EDITABLE
		setState(State.EDITABLE);
	}

	public void afterSaveFailure() {
		// Set the edited object to the display
		setEditedObject(editedObject);
		// Set the state to EDITION
		setState(State.EDITION);
	}

	public void afterDeleteSuccess() {
		// Clear the current content of the input components
		setEditedObject(null);
		// Set the state to BLANK
		setState(State.BLANK);
	}

	public void afterDeleteFailure() {
		// Set the edited object to the display
		setEditedObject(editedObject);
		// Set the state to EDITABLE
		setState(State.EDITABLE);
	}

	@UiHandler(value = "newButton")
	void onNewClicked(ClickEvent event) {
		// Clear the current content of the input components
		setEditedObject(null);
		// Change the mode to EDITION
		setState(State.NEW);
	}

	@UiHandler(value = "editButton")
	void onEditClicked(ClickEvent event) {
		// Change the mode to EDITION
		setState(State.EDITION);
	}

	@UiHandler(value = "saveButton")
	void onSaveClicked(ClickEvent event) {
		if (saveHandler != null) {
			// Get the edited object from the form and fire the save event
			saveHandler.onSave(new SaveEvent<T>(getEditedObject(), State.NEW.equals(getState())));
		}
	}

	@UiHandler(value = "deleteButton")
	void onDeleteClicked(ClickEvent event) {
		// Check if a confirmation must be asked
		boolean confirmed = deleteConfirmationRequired ? Window.confirm("Are you sure you want to delete this entry ?") : true;

		if (confirmed && deleteHandler != null) {
			// Get the edited object from the form and fire the delete event
			deleteHandler.onDelete(new DeleteEvent<T>(getEditedObject()));
		}

	}

	@UiHandler(value = "cancelButton")
	void onCancelClicked(ClickEvent event) {
		// If the edit panel was in the NEW mode, the canceling will set the
		// panel to the BLANK mode.
		switch (getState()) {
		case NEW:
			// Clear the current content of the input components
			setEditedObject(null);
			// Set the state to BLANK
			setState(State.BLANK);
			break;
		// If the edit panel was in the EDITION mode, the canceling will set the
		// panel to the EDITABLE mode.
		case EDITION:
		default:
			// Refill the fields with the original values
			setEditedObject(editedObject);
			// Set the state to EDITABLE
			setState(State.EDITABLE);
			break;
		}
	}

	/**
	 * This method allows adding a Handler on the EditPanel using the
	 * {@link UiHandler} annotation in the class using the EditPanel. For
	 * example :</br>
	 * 
	 * <pre>
	 * &#064;UiField
	 * EditPanel&lt;T&gt; editPanel;
	 * 
	 * &#064;UiHandler(&quot;editPanel&quot;)
	 * void onDelete(DeleteEvent&lt;T&gt; event) {
	 * 	System.out.println(&quot;Delete event received !&quot;);
	 * }
	 * </pre>
	 * 
	 * @param handler
	 *            The DeleteHandler to call on a click on the delete button.
	 * @return A HandlerRegistration that can be used to remove the
	 *         DeleteHandler.
	 */
	public HandlerRegistration addDeleteHandler(DeleteHandler<T> handler) {
		this.deleteHandler = handler;
		return new HandlerRegistration() {
			public void removeHandler() {
				deleteHandler = null;
			}
		};
	}

	/**
	 * This method allows adding a Handler on the EditPanel using the
	 * {@link UiHandler} annotation in the class using the EditPanel. For
	 * example :</br>
	 * 
	 * <pre>
	 * &#064;UiField
	 * EditPanel&lt;T&gt; editPanel;
	 * 
	 * &#064;UiHandler(&quot;editPanel&quot;)
	 * void onSave(SaveEvent&lt;T&gt; event) {
	 * 	System.out.println(&quot;Save event received !&quot;);
	 * }
	 * </pre>
	 * 
	 * @param handler
	 *            The SaveHandler to call on a click on the save button.
	 * @return A HandlerRegistration that can be used to remove the SaveHandler.
	 */
	public HandlerRegistration addSaveHandler(SaveHandler<T> handler) {
		this.saveHandler = handler;
		return new HandlerRegistration() {
			public void removeHandler() {
				saveHandler = null;
			}
		};
	}

	/*
	 * The following four method are inherited from the HasWidgets interface,
	 * and we simply delegate the action to the FlowPanel of the EditPanel. This
	 * allows including widgets between the <EditPanel/> brackets in the ui.xml
	 * files using the EditPanel.
	 */

	public void add(Widget w) {
		fieldsPanelContainer.add(w);
	}

	public void clear() {
		fieldsPanelContainer.clear();
	}

	public Iterator<Widget> iterator() {
		return fieldsPanelContainer.iterator();
	}

	public boolean remove(Widget w) {
		return fieldsPanelContainer.remove(w);
	}

}

package it.uniroma2.partyflow.errorhandlers.handlerui;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.util.Objects;

/**
 * Error handler responsible for managing errors in the JavaFX graphical
 * user interface.
 *
 * <p>Implements the Singleton pattern and provides methods for displaying
 * error dialogs and highlighting missing mandatory form elements.</p>
 *
 * <p>Requests application termination while respecting the JavaFX
 * application lifecycle, allowing the launcher to perform resource cleanup.</p>
 */


public class HandlerJavaFxGUIError implements HandlerUIError {
    private static HandlerJavaFxGUIError instance = null;

    private HandlerJavaFxGUIError(){
    }

    public static HandlerJavaFxGUIError getInstance(){
        if(instance == null){
            instance = new HandlerJavaFxGUIError();
        }
        return instance;
    }


    /**
     * Displays an error message through a JavaFX alert dialog
     * and waits until the user dismisses it.
     *
     * @param mex the error message to display
     */

    @Override
    public void errorMessage(String mex){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(mex);
        alert.showAndWait();
    }

    /**
     * Displays an information message through a JavaFX alert dialog
     * and waits until the user dismisses it.
     *
     * @param mex the error message to display
     */

    public void infoMessage(String mex){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(mex);
        alert.showAndWait();
    }



    /**
     * Requests an orderly shutdown of the JavaFX application.
     *
     * <p>This normally triggers GUIApplication.stop(), allowing cleanup
     * operations to release resources before shutdown.</p>
     *
     * <p>Unlike {@code System.exit()}, {@link Platform#exit()} does not
     * directly terminate the JVM, but requests the shutdown of the
     * JavaFX runtime. It also does not immediately interrupt the
     * calling method.</p>
     *
     * <p>The JVM terminates automatically if no non-daemon threads
     * remain active.</p>
     */

    @Override
    public void terminateApplication() {
        Platform.exit();
    }


    /**
     * Highlights a missing mandatory text field with a red border.
     *
     * <p>Registers a listener that restores the original style
     * when the text changes and then unregisters itself.
     * Prevents duplicate highlighting and listener registration.</p>
     *
     * @param field the text field to highlight
     */

    public void highlightMissingFormElement(TextField field) {
        String errorKey = "errorHighlighted";
        // If the text field is already highlighted, do nothing.
        // This prevents multiple listeners from being registered.
        if (Boolean.TRUE.equals(field.getProperties().get(errorKey))) {
            return;
        }

        // Mark the text field as highlighted.
        field.getProperties().put(errorKey, true);

        // Save the original style before applying the error style.
        String oldStyle = field.getStyle();

        // Highlight the text field with a red border.
        field.setStyle(oldStyle + "; -fx-border-color: red;");

        /*
         * Creates an instance of an anonymous class, which is a class
         * without an explicit name, declared and instantiated at the same time.
         *
         * It allows us to implement the ChangeListener interface without
         * defining a separate named class, since this behavior is only needed here.
         *
         * ChangeListener is a JavaFX interface used to observe changes
         * in the value of an observable property.
         *
         * Observing a property means that, once the listener is registered,
         * it is automatically notified whenever the property's value changes.
         *
         * The changed() method defines the behavior executed in response
         * to such a notification and receives both the old and the new value
         * of the observed property.
         *
         * JavaFX implements the GoF Observer design pattern through
         * the ObservableValue and ChangeListener interfaces.
         */

        ChangeListener<String> listener = new ChangeListener<>() {

            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue,
                    String newValue) {

                // Restore the original style.
                field.setStyle(oldStyle);

                // Remove the highlighted flag, allowing future errors.
                field.getProperties().remove(errorKey);

                // Unregister this listener because its task is complete.
                field.textProperty().removeListener(this);
            }
        };

        // Register the listener.
        field.textProperty().addListener(listener);
    }


    /**
     * Highlights a container of radio buttons with a red border
     * when a mandatory selection is missing.
     *
     * <p>Registers a listener on the associated ToggleGroup that
     * restores the original style when a radio button is selected
     * and then unregisters itself.
     * Prevents duplicate highlighting and listener registration.</p>
     *
     * @param pane the container to highlight
     * @throws java.util.NoSuchElementException if no associated
     * ToggleGroup is found among the direct children of the pane
     */

    public void highlightMissingFormElement(AnchorPane pane) {
        String errorKey = "errorHighlighted";

        // Avoid highlighting the same pane multiple times.
        if (Boolean.TRUE.equals(pane.getProperties().get(errorKey))) {
            return;
        }

        // Mark the pane field as highlighted.
        pane.getProperties().put(errorKey, true);

        // Save the original style and highlight the pane.
        String oldStyle = pane.getStyle();
        pane.setStyle(oldStyle + "; -fx-border-color: red;");

        /*
         * The AnchorPane visually contains the RadioButtons, while the
         * ToggleGroup logically groups them and ensures that at most
         * one RadioButton can be selected at a time.
         *
         * Since we already know which AnchorPane has been highlighted,
         * we need to retrieve its associated ToggleGroup to observe
         * changes in the selected RadioButton.
         *
         * When the user selects a RadioButton, the observer is notified
         * and restores the original style of the AnchorPane.
         */

        /*
         * 1. getChildren(): retrieves the list of direct children of the AnchorPane.
         *
         * 2. stream(): creates a stream to process the elements of the list.
         *
         * 3. filter(): keeps only objects that are instances of RadioButton.
         *
         * 4. map(): casts each remaining object from Node to RadioButton.
         *
         * 5. map(): retrieves the ToggleGroup associated with each RadioButton.
         *
         * 6. filter(): removes null references, since some RadioButtons
         *    may not belong to a ToggleGroup.
         *
         * 7. findFirst(): retrieves the first available ToggleGroup and
         *    returns it as an Optional, since a result may not exist.
         *
         * 8. orElseThrow(): extracts the ToggleGroup from the Optional or
         *    throws a NoSuchElementException if no ToggleGroup is found.
         *
         * The retrieved ToggleGroup is then observed to detect when a
         * RadioButton is selected and restore the original AnchorPane style.
         */
        ToggleGroup group = pane.getChildren().stream()
                .filter(RadioButton.class::isInstance)
                .map(RadioButton.class::cast)
                .map(RadioButton::getToggleGroup)
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow();

        // Observe changes to the selected RadioButton.
        ChangeListener<Toggle> listener = new ChangeListener<>() {

            @Override
            public void changed(
                    ObservableValue<? extends Toggle> observable,
                    Toggle oldValue,
                    Toggle newValue) {

                // Restore the original style when a RadioButton is selected.
                if (newValue != null) {
                    pane.setStyle(oldStyle);
                    pane.getProperties().remove(errorKey);

                    // Unregister the listener after restoring the style.
                    group.selectedToggleProperty().removeListener(this);
                }
            }
        };

        group.selectedToggleProperty().addListener(listener);
    }



}

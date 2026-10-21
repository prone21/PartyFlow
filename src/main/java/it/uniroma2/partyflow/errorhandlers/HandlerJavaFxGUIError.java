package it.uniroma2.partyflow.errorhandlers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.util.Objects;


/**
 * This class handles the error that may occur in a JavaFX-based GUI.
 *
 */

public class HandlerJavaFxGUIError implements HandlerUIError{
    private static HandlerJavaFxGUIError instance = null;

    private HandlerJavaFxGUIError(){
    }

    public static HandlerJavaFxGUIError getInstance(){
        if(instance == null){
            instance = new HandlerJavaFxGUIError();
        }
        return instance;
    }

    // it shows a pop-up with a message of error
    public void alertErrorMessage(String mex){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(mex);
        alert.showAndWait();
    }

    // Highlights an empty TextField with a red border and automatically
    // restores its original style when the text changes.
    public void highlightMissingFormElement(TextField field) {
        String ERROR_KEY = "errorHighlighted";
        // If the text field is already highlighted, do nothing.
        // This prevents multiple listeners from being registered.
        if (Boolean.TRUE.equals(field.getProperties().get(ERROR_KEY))) {
            return;
        }

        // Mark the text field as highlighted.
        field.getProperties().put(ERROR_KEY, true);

        // Save the original style before applying the error style.
        String oldStyle = field.getStyle();

        // Highlight the text field with a red border.
        field.setStyle(oldStyle + "; -fx-border-color: red;");

        // Create a listener that observes changes to the text field's text.
        ChangeListener<String> listener = new ChangeListener<>() {

            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue,
                    String newValue) {

                // Restore the original style.
                field.setStyle(oldStyle);

                // Remove the highlighted flag, allowing future errors.
                field.getProperties().remove(ERROR_KEY);

                // Unregister this listener because its task is complete.
                field.textProperty().removeListener(this);
            }
        };

        // Register the listener.
        field.textProperty().addListener(listener);
    }



    public void highlightMissingFormElement(AnchorPane pane) {
        String ERROR_KEY = "errorHighlighted";

        // Avoid highlighting the same pane multiple times.
        if (Boolean.TRUE.equals(pane.getProperties().get(ERROR_KEY))) {
            return;
        }

        pane.getProperties().put(ERROR_KEY, true);

        // Save the original style and highlight the pane.
        String oldStyle = pane.getStyle();
        pane.setStyle(oldStyle + "; -fx-border-color: red;");

        // Retrieve the ToggleGroup from one of the RadioButtons.
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
                    pane.getProperties().remove(ERROR_KEY);

                    // Unregister the listener after restoring the style.
                    group.selectedToggleProperty().removeListener(this);
                }
            }
        };

        group.selectedToggleProperty().addListener(listener);
    }



}

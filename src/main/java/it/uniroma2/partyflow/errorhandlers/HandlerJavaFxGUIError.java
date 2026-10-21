package it.uniroma2.partyflow.errorhandlers;

import javafx.scene.Node;
import javafx.scene.control.Alert;

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

    public void alertMessage(String mex){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(mex);
        alert.showAndWait();
    }


    public void highlightMissedParameter(Node field){
        String oldStyleField = field.getStyle();
        field.setStyle(oldStyleField + "-fx-border-color: red;");

    }

}

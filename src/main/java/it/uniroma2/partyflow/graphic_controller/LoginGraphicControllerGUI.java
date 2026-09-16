package it.uniroma2.partyflow.graphic_controller;

import it.uniroma2.partyflow.utilities.NavigatorSingleton;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginGraphicControllerGUI {

    @FXML
    public void goBackward() throws IOException {
        NavigatorSingleton nav = NavigatorSingleton.getInstance();
        nav.goBackward();
    }

    @FXML
    public void submitCredentials(){
        System.out.println("Hola boludo!");
    }

    @FXML
    public void signUp(MouseEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        NavigatorSingleton nav = NavigatorSingleton.getInstance(stage);
        nav.gotoView("/it/uniroma2/partyflow/view/view_in_common/signUp.fxml");
    }

    @FXML
    public void continueWithGoogle(){
        System.out.println("Google log-in is not implemented yet.");
    }

    @FXML
    public void continueWithFacebook(){
        System.out.println("Facebook log-in is not implemented yet.");
    }


}

package it.uniroma2.partyflow.graphic_controller;

import it.uniroma2.partyflow.utilities.NavigatorSingleton;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpGraphicControllerUI {

    public void createAccount(){
        System.out.println("Hola perro negro!");
    }

    public void goBackward(MouseEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        NavigatorSingleton nav = NavigatorSingleton.getInstance(stage);
        nav.gotoView("/it/uniroma2/partyflow/view/view_in_common/firstScene.fxml");
    }
}

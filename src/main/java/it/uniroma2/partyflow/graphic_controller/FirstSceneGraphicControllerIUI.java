package it.uniroma2.partyflow.graphic_controller;

import it.uniroma2.partyflow.utilities.NavigatorSingleton;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;

public class FirstSceneGraphicControllerIUI {
    @FXML
    public void loginView(MouseEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        NavigatorSingleton nav = NavigatorSingleton.getInstance(stage);
        nav.gotoView("/it/uniroma2/partyflow/view/view_in_common/login.fxml");
    }

    @FXML
    public void signUpView(MouseEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        NavigatorSingleton nav = NavigatorSingleton.getInstance(stage);
        nav.gotoView("/it/uniroma2/partyflow/view/view_in_common/signUp.fxml");
    }
}

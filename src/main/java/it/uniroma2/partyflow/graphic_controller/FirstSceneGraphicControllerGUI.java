package it.uniroma2.partyflow.graphic_controller;
import it.uniroma2.partyflow.utilities.NavigatorSingleton;
import javafx.fxml.FXML;
import java.io.IOException;

public class FirstSceneGraphicControllerGUI {
    @FXML
    public void login() throws IOException {
        NavigatorSingleton nav = NavigatorSingleton.getInstance();
        nav.gotoView("/it/uniroma2/partyflow/view/view_in_common/login.fxml");
    }

    @FXML
    public void signUp () throws IOException {
        NavigatorSingleton nav = NavigatorSingleton.getInstance();
        nav.gotoView("/it/uniroma2/partyflow/view/view_in_common/signUp.fxml");
    }
}

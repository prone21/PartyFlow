package it.uniroma2.partyflow.graphic_controller;

import it.uniroma2.partyflow.utilities.NavigatorSingleton;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpGraphicControllerGUI {

    public void createAccount(){
        System.out.println("Hola perro negro!");
    }

    public void goBackward() throws IOException {
        NavigatorSingleton nav = NavigatorSingleton.getInstance();
        nav.goBackward();
    }
}

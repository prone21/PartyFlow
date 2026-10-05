package it.uniroma2.partyflow.utilities;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class NavigatorSingleton {
    private static NavigatorSingleton instance = null;
    private final Stage stage; // The Stage whose views are managed by the Navigator.
    private Scene prevScene;

    private NavigatorSingleton(Stage stage){
        this.stage = stage;
    }

    public static NavigatorSingleton getInstance(Stage stage){
        if (NavigatorSingleton.instance == null){
            NavigatorSingleton.instance = new NavigatorSingleton(stage);
        }
        return NavigatorSingleton.instance;
    }

    // Is used to obtain the existing instance without passing the Stage.
    public static NavigatorSingleton getInstance(){
        return NavigatorSingleton.instance;
    }

    public void gotoView(String viewFXMLPath) throws IOException {
        prevScene = stage.getScene();

        FXMLLoader loader =
                new FXMLLoader(getClass().getResource(viewFXMLPath));

        Scene scene = new Scene(loader.load());

        stage.setTitle("PartyFlow");
        stage.setScene(scene);

        stage.show();
    }


    public void gotoView(Scene scene) {

        stage.setTitle("PartyFlow");
        stage.setScene(scene);

        stage.show();
    }

    // Is used to go back at the precedent view
    public void goBackward() {
        NavigatorSingleton nav = NavigatorSingleton.getInstance();
        nav.gotoView(prevScene);
    }

}

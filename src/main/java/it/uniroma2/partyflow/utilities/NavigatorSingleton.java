package it.uniroma2.partyflow.utilities;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class NavigatorSingleton {
    private static NavigatorSingleton instance = null;
    private Stage stage;
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

    public static NavigatorSingleton getInstance(){
        return NavigatorSingleton.instance;
    }

    public void gotoView(String viewPath) throws IOException {
        prevScene = stage.getScene();

        FXMLLoader loader =
                new FXMLLoader(getClass().getResource(viewPath));

        Scene scene = new Scene(loader.load());

        stage.setTitle("PartyFlow");
        stage.setScene(scene);

        stage.show();
    }

    public void gotoView(Scene scene) throws IOException {

        stage.setTitle("PartyFlow");
        stage.setScene(scene);

        stage.show();
    }


    public void goBackward() throws IOException {
        NavigatorSingleton nav = NavigatorSingleton.getInstance();
        nav.gotoView(prevScene);
    }

}

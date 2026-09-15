package it.uniroma2.partyflow.utilities;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class NavigatorSingleton {
    private static NavigatorSingleton instance = null;
    private Stage stage;

    private NavigatorSingleton(Stage stage){
        this.stage = stage;
    }

    public static NavigatorSingleton getInstance(Stage stage){
        if (NavigatorSingleton.instance == null){
            NavigatorSingleton.instance = new NavigatorSingleton(stage);
        }
        return NavigatorSingleton.instance;
    }

    public void gotoView(String view) throws IOException {
        FXMLLoader loader =
                new FXMLLoader(getClass().getResource(view));

        Scene scene = new Scene(loader.load());

        stage.setTitle("PartyFlow");
        stage.setScene(scene);

        stage.show();
    }

}

package it.uniroma2.partyflow.utilities;

import it.uniroma2.partyflow.exceptions.InvalidStateException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class NavigatorSingleton {
    // Responsible for displaying views defined in FXML files. It allows the view navigation.

    private final Stage stage; // The Stage in which the views (scenes) are displayed.
    private Scene prevScene;
    private static NavigatorSingleton instance = null;

    private NavigatorSingleton(Stage stage) {
        this.stage = stage;
    }


    // guarantee singleton
    public static NavigatorSingleton getInstance(Stage stage){
        if(instance == null){
            instance = new NavigatorSingleton(stage);
        }
        return instance;
    }

    public static NavigatorSingleton getInstance() throws InvalidStateException {
        if(instance == null){
            throw new InvalidStateException("NavigatorSingleton is not initialized. " +
                    "Use getInstance(Stage) to initialize it first.");
        }
        else{
            return instance;
        }
    }


    //Given the view's path, it sets the new scene on the stage
    public void gotoView(String viewFXMLPath) throws IOException {
        this.prevScene = this.stage.getScene(); // obtaining the actual scene that is going to be the prevScene

        FXMLLoader loader =
                new FXMLLoader(getClass().getResource(viewFXMLPath));

        Scene scene = new Scene(loader.load()); // load obtains the root node by fxml file

        this.stage.setTitle("PartyFlow");
        this.stage.setScene(scene);

        this.stage.show();
    }

    //sets the given scene on the stage
    private void gotoScene(Scene scene) {
        this.stage.setTitle("PartyFlow");
        this.stage.setScene(scene);

        this.stage.show();
    }

    //It's used to go back at the precedent view
    public void goBackward() {
        this.gotoScene(this.prevScene);
    }

}

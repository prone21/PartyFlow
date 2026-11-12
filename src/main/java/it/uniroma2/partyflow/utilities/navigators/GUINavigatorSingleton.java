package it.uniroma2.partyflow.utilities.navigators;

import it.uniroma2.partyflow.exceptions.systemexception.InternalException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Responsible for displaying views defined in FXML files.
 * It allows the view navigation.
 */

public class GUINavigatorSingleton {
    private final Stage stage; // The Stage in which the views (scenes) are displayed.
    private Scene prevScene;
    private static GUINavigatorSingleton instance = null;

    private GUINavigatorSingleton(Stage stage) {
        this.stage = stage;
    }


    // guarantee singleton
    public static GUINavigatorSingleton getInstance(Stage stage){
        if(instance == null){
            instance = new GUINavigatorSingleton(stage);
        }
        return instance;
    }

    public static GUINavigatorSingleton getInstance() throws InternalException {
        if(instance == null){
            throw new InternalException("NavigatorSingleton is not initialized. " +
                    "Use getInstance(Stage) to initialize it first.");
        }
        else{
            return instance;
        }
    }


    //Given the view's path, it sets the new scene on the stage
    public void goToView(String viewFXMLPath) throws InternalException {
        try{
            this.prevScene = this.stage.getScene(); // obtaining the actual scene that is going to be the prevScene

            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource(viewFXMLPath));

            Scene scene = new Scene(loader.load()); // load obtains the root node by fxml file

            this.stage.setTitle("PartyFlow");
            this.stage.setScene(scene);

            this.stage.show();
        }
        catch (IOException e){
            throw new InternalException("Failed to load FXML view: " + viewFXMLPath);
        }
    }

    //sets the given scene on the stage
    private void goToScene(Scene scene) {
        this.stage.setTitle("PartyFlow");
        this.stage.setScene(scene);

        this.stage.show();
    }

    //It's used to go back at the precedent view
    public void goBackward() {
        this.goToScene(this.prevScene);
    }

}

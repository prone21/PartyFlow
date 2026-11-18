package it.uniroma2.partyflow.utilities.navigators;

import it.uniroma2.partyflow.exceptions.systemexception.InternalException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Singleton responsible for managing navigation between JavaFX views.
 *
 * <p>Loads views defined in FXML files and displays them as scenes
 * within the same Stage. It also stores the previous scene
 * to support backward navigation.</p>
 */


public class GUINavigatorSingleton {
    private final Stage stage; // The Stage in which the views (scenes) are displayed.
    private Scene prevScene;
    private static GUINavigatorSingleton instance = null;

    private GUINavigatorSingleton(Stage stage) {
        this.stage = stage;
    }


    // Ensures a single instance
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


    /**
     * Navigates to the view defined by the specified FXML file.
     *
     * <p>Stores the current scene to support backward navigation,
     * loads the FXML file, creates a new scene, and displays it
     * within the current Stage.</p>
     *
     * @param viewFXMLPath the resource path of the FXML file to load
     * @throws InternalException if an IOException occurs while loading the FXML file
     */

    public void goToView(String viewFXMLPath) throws InternalException {
        try{
            this.prevScene = this.stage.getScene(); // obtaining the actual scene that is going to be the prevScene

            // FXMLLoader is the class responsible for converting an FXML file into JavaFX objects.
            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource(viewFXMLPath));

            /* The load() method converts the FXML file into JavaFX objects,
             * preserves the hierarchical structure defined in the file,
             * and returns the root node of the resulting scene graph.
             */
            Scene scene = new Scene(loader.load()); // load obtains the root node by fxml file

            this.stage.setTitle("PartyFlow");
            this.stage.setScene(scene);

            this.stage.show();
        }
        catch (IOException _){
            throw new InternalException("Failed to load FXML view: " + viewFXMLPath);
        }
    }

    //sets the given scene on the stage
    private void goToScene(Scene scene) {
        this.stage.setTitle("PartyFlow");
        this.stage.setScene(scene);

        this.stage.show();
    }

    //It's used to go back at the previous view
    public void goBackward() {

        if(this.prevScene != null) {
            this.goToScene(this.prevScene);
        }
    }

}

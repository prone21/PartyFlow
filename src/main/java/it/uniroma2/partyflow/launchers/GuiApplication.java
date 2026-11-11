package it.uniroma2.partyflow.launchers;
import it.uniroma2.partyflow.daos.ConnectionFactory;
import it.uniroma2.partyflow.enums.PersistenceType;
import it.uniroma2.partyflow.errorhandlers.HandlerSystemError;
import it.uniroma2.partyflow.exceptions.systemexception.InternalException;
import it.uniroma2.partyflow.utilities.navigators.GUINavigatorSingleton;
import it.uniroma2.partyflow.utilities.configurators.PersistenceConfigurator;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

/**
 * Entry point for the JavaFX GUI application.
 * Extends {@link Application} to integrate with JavaFX lifecycle.
 *
 */

public class GuiApplication extends Application implements UIApplication {

    /*
     * Starts the JavaFX application.
     *
     * <p>Loads custom fonts, initializes the navigator singleton,
     * and displays the login screen.</p>
     *
     * @param stage the primary stage provided by JavaFX
     */


    // ----------------- UIApplication java methods ------------------
    @Override
    public void launch(){
        Application.launch(GuiApplication.class);
    }

    @Override
    public void close(){
        Platform.exit();
    }
    // ---------------------------------------------------------------

    @Override
    public void start(Stage stage){
        try {
            GUINavigatorSingleton nav = GUINavigatorSingleton.getInstance(stage);
            nav.gotoView("/it/uniroma2/partyflow/view/view_in_common/firstScene.fxml");
        }
        catch (InternalException e){
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(e);
        }

    }



    /**
     * Called by JavaFX when the application is stopping.
     *
     * @throws Exception if cleanup fails
     * When is stop() called?
     * There are mainly two situations:
     * 1. The user closes the last JavaFX window, if implicitExit is enabled (default).
     * 2. The program calls Platform.exit() after the JavaFX runtime has started.
     * In both cases, JavaFX normally invokes stop().
     * IMPORTANT:
     * Calling System.exit() does not guarantee that stop() will be invoked.
     */

    @Override
    public void stop() throws Exception {
        this.cleanup();
        super.stop();
    }

    /**
     * Performs cleanup: closes database connection if MySQL.
     */
    private void cleanup() {
        HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();

        try{
            PersistenceConfigurator pc = PersistenceConfigurator.getInstance();
            if(pc.getPersistenceType() == PersistenceType.MySQL){
                ConnectionFactory cf = ConnectionFactory.getInstance();
                cf.closeConnection();
                handlerSystemError.logInfo("Database connection closed");
            }
        }

        catch (Exception e){
            handlerSystemError.logError("Error cleaning up", e);
        }

        handlerSystemError.logInfo("GUI application shutdown complete");
    }


}

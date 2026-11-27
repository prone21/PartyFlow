package it.uniroma2.partyflow.launchers;

import it.uniroma2.partyflow.daos.ConnectionFactory;
import it.uniroma2.partyflow.enums.PersistenceType;
import it.uniroma2.partyflow.errorhandlers.HandlerSystemError;
import it.uniroma2.partyflow.exceptions.systemexception.ApplicationTerminationException;
import it.uniroma2.partyflow.graphic_controllers.cli.graphic_controllers_in_common.FirstSceneGraphicControllerCLI;
import it.uniroma2.partyflow.utilities.configurators.PersistenceConfigurator;

/**
 * Entry point for the CLI application.
 *
 * <p>Manages the CLI application lifecycle, including startup,
 * execution, and resource cleanup during shutdown.</p>
 */

public class CLIApplication implements UIApplication {

    @Override
    public void launch() {
        try {
            start();
        } catch (ApplicationTerminationException _) {
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logWarning(
                    "CLI application terminated due to a fatal error."
            );
        } finally {
            cleanup();
        }
    }


    private void start() {

        FirstSceneGraphicControllerCLI controller =
                new FirstSceneGraphicControllerCLI();

        controller.execute();
    }



    /**
     * Performs cleanup: closes database connection if MySQL.
     */
    private void cleanup() {
        HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();

        try{
            PersistenceConfigurator pc = PersistenceConfigurator.getInstance();
            if(pc.getPersistenceType() == PersistenceType.MY_SQL){
                ConnectionFactory.closeConnection();
                handlerSystemError.logInfo("Database connection closed");
            }
        }

        catch (Exception e){
            handlerSystemError.logError("Error cleaning up", e);
        }

        handlerSystemError.logInfo("CLI application shutdown complete");
    }
}
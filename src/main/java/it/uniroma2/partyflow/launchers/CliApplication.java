package it.uniroma2.partyflow.launchers;

import it.uniroma2.partyflow.daos.ConnectionFactory;
import it.uniroma2.partyflow.enums.PersistenceType;
import it.uniroma2.partyflow.errorhandlers.HandlerSystemError;
import it.uniroma2.partyflow.graphic_controllers.CLI.FirstSceneGraphicControllerCLI;
import it.uniroma2.partyflow.utilities.configurators.PersistenceConfigurator;

public class CliApplication implements UIApplication {

    // ----------------- UIApplication java methods ------------------
    @Override
    public void launch(){
        this.start();
    }

    @Override
    public void close(){
        this.cleanup();
        System.exit(0);
    }
    // ---------------------------------------------------------------




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
            if(pc.getPersistenceType() == PersistenceType.MySQL){
                ConnectionFactory cf = ConnectionFactory.getInstance();
                cf.closeConnection();
                handlerSystemError.logInfo("Database connection closed");
            }
        }

        catch (Exception e){
            handlerSystemError.logError("Error cleaning up", e);
        }

        handlerSystemError.logInfo("CLI application shutdown complete");
    }
}
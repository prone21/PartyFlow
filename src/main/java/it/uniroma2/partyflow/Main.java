package it.uniroma2.partyflow;
import it.uniroma2.partyflow.dao.DaoAbstractFactory;
import it.uniroma2.partyflow.enums.PersistenceType;
import it.uniroma2.partyflow.enums.UIType;
import it.uniroma2.partyflow.errorhandlers.HandlerErrorFactory;
import it.uniroma2.partyflow.launcher.CliApplication;
import it.uniroma2.partyflow.launcher.GuiApplication;
import it.uniroma2.partyflow.utilities.UIConfigurator;
import it.uniroma2.partyflow.utilities.PersistenceConfigurator;
import javafx.application.Application;


public class Main {

    public static void main() {
        // It defines the execution context.
        UIConfigurator uiconf = UIConfigurator.getInstance();
        PersistenceConfigurator persconf = PersistenceConfigurator.getInstance();

        configurePersistence(persconf.getPersistenceType());
        launchInterface(uiconf.getUI());
    }

    private static void configurePersistence(PersistenceType persistenceType){
        DaoAbstractFactory.setPersistenceType(persistenceType);
    }

    private static void launchInterface(UIType interfaceType){
        HandlerErrorFactory hef = HandlerErrorFactory.getInstance();
        hef.getHandlerUIError(interfaceType);

         switch (interfaceType){
             case GUI -> Application.launch(GuiApplication.class);
             case CLI -> new CliApplication().start();
             default -> Application.launch(GuiApplication.class);
         }

         /*
        if (interfaceType == UIType.GUI) {
            Application.launch(GuiApplication.class);
        } else if (interfaceType == UI) {
            new CliApplication().start();
        } else {
            System.out.println("Unknown interface type: " + interfaceType + ". Defaulting to GUI.");
            Application.launch(GuiApplication.class);
        }


          */



    }



}
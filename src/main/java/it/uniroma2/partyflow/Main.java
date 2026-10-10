package it.uniroma2.partyflow;
import it.uniroma2.partyflow.dao.DaoAbstractFactory;
import it.uniroma2.partyflow.enums.PersistenceType;
import it.uniroma2.partyflow.enums.UIType;
import it.uniroma2.partyflow.errorhandlers.HandlerErrorFactory;
import it.uniroma2.partyflow.errorhandlers.HandlerSystemError;
import it.uniroma2.partyflow.launcher.CliApplication;
import it.uniroma2.partyflow.launcher.GuiApplication;
import it.uniroma2.partyflow.utilities.Configurator;
import javafx.application.Application;

import java.io.IOException;

public class Main {
    //software configuration variables

    public static void main() {
        HandlerErrorFactory hef = HandlerErrorFactory.getInstance();

        try {
            Configurator cr = Configurator.getConfigurator();
            configurePersistence(cr.getPersistenceLayer());
            launchInterface(cr.getUI());

        }
        catch (IOException e){
            HandlerSystemError hse = hef.getHandlerSystemError();
            hse.severeLog("IOException", e);
        }

    }

    private static void configurePersistence(PersistenceType persistenceType){
        DaoAbstractFactory.getFactory(persistenceType);

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
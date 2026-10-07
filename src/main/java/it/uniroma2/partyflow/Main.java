package it.uniroma2.partyflow;
import it.uniroma2.partyflow.launcher.GuiApplication;
import it.uniroma2.partyflow.utilities.Configurator;
import javafx.application.Application;

import java.io.IOException;

public class Main {
    //software configuration variables

    private static final String DEFAULT_PERSISTENCE = "dbms-mysql";
    private static final String DEFAULT_INTERFACE = "gui-javafx";

   /* private static final String PERSISTENCE_MYSQL = "mysql";
    private static final String PERSISTENCE_CSV = "csv";
    private static final String PERSISTENCE_INMEMORY = "inmemory";
*/
    private static final String INTERFACE_GUI = "gui-javafx";
    private static final String INTERFACE_CLI = "cli";


    public static void main() {

        try {
            Configurator cr = Configurator.getConfigurator();
            launchInterface(cr.getUI());

        }
        catch (IOException e){
            System.out.println("Oye maestro! No te olvidaremos");
            e.printStackTrace();
        }



    }

    private static void launchInterface(String interfaceType){

        if (INTERFACE_GUI.equals(interfaceType)) {
            Application.launch(GuiApplication.class);
        } else if (INTERFACE_CLI.equals(interfaceType)) {
            System.out.println("CLI");
           // new CliApplication().start();
        } else {
            System.out.println("Unknown interface type: " + interfaceType + ". Defaulting to GUI.");
            Application.launch(GuiApplication.class);
        }

    }



}
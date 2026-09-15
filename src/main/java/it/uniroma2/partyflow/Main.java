package it.uniroma2.partyflow;
import it.uniroma2.partyflow.launcher.GuiApplication;
import javafx.application.Application;

public class Main {
    private static final String DEFAULT_PERSISTENCE = "mysql";
    private static final String DEFAULT_INTERFACE = "gui";

    private static final String PERSISTENCE_MYSQL = "mysql";
    private static final String PERSISTENCE_CSV = "csv";
    private static final String PERSISTENCE_INMEMORY = "inmemory";

    private static final String INTERFACE_GUI = "gui";
    private static final String INTERFACE_CLI = "cli";


    public static void main(String[] args) {
        // Parse command-line arguments
        String persistenceType = args.length > 0 ? args[0].toLowerCase() : DEFAULT_PERSISTENCE;
        String interfaceType = args.length > 1 ? args[1].toLowerCase() : DEFAULT_INTERFACE;

        launchInterface(interfaceType);

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
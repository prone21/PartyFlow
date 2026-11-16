package it.uniroma2.partyflow;
import it.uniroma2.partyflow.launchers.UIApplication;
import it.uniroma2.partyflow.launchers.UIApplicationFactory;

/**
 * Entry point of the PartyFlow application.
 *
 * <p>Retrieves the appropriate UI implementation through
 * UIApplicationFactory and launches the application
 * according to the configured user interface type.</p>
 */


public class Main {
    static void main() {
        launchInterface();
    }

    private static void launchInterface(){
        UIApplicationFactory uaf = UIApplicationFactory.getInstance();
        UIApplication uia = uaf.getUIApplication();
        uia.launch();
    }



}
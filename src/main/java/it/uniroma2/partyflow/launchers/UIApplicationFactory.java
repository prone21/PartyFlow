package it.uniroma2.partyflow.launchers;

import it.uniroma2.partyflow.utilities.configurators.UIConfigurator;

import java.io.IOException;


/**
 * Factory responsible for creating the appropriate PartyFlow UI application.
 *
 * <p>Implements the Singleton pattern and selects the UI implementation
 * (CLI or GUI) based on the configuration provided by UIConfigurator.
 * If the configuration cannot be loaded, the GUI implementation
 * is used as the default.</p>
 */


public class UIApplicationFactory {
    private static UIApplicationFactory instance = null;

    private UIApplicationFactory(){}

    // Ensures a single instance
    public static UIApplicationFactory getInstance(){
        if(instance == null){
            instance = new UIApplicationFactory();
        }
        return instance;
    }

    public UIApplication getUIApplication(){
        try{
            UIConfigurator uic = UIConfigurator.getInstance();
            return switch (uic.getUI()){
                case JAVA_FX_GUI -> new GUIApplication();
                case CLI -> new CLIApplication();
            };
        }

        catch (IOException _){
            // default UI
            return new GUIApplication();
        }


    }
}

package it.uniroma2.partyflow.launchers;

import it.uniroma2.partyflow.utilities.configurators.UIConfigurator;

import java.io.IOException;

public class UIApplicationFactory {
    private static UIApplicationFactory instance = null;

    private UIApplicationFactory(){}

    // guarantee singleton
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
                case JavaFxGUI -> new GUIApplication();
                case CLI -> new CLIApplication();
            };
        }

        catch (IOException e){
            // default UI
            return new GUIApplication();
        }


    }
}

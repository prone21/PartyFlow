package it.uniroma2.partyflow.launchers;

import it.uniroma2.partyflow.utilities.configurators.UIConfigurator;

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
        UIConfigurator uic = UIConfigurator.getInstance();
        return switch (uic.getUI()){
            case JavaFxGUI -> new GuiApplication();
            case CLI -> new CliApplication();
        };
    }
}

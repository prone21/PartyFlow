package it.uniroma2.partyflow.errorhandlers;

import it.uniroma2.partyflow.enums.UIType;
import it.uniroma2.partyflow.utilities.configurators.UIConfigurator;

public class HandlerErrorFactory {
    // Its responsibility is to provide the appropriate error handler.

    private static HandlerErrorFactory instance = null;

    private HandlerErrorFactory (){}

    // Guarantee Singleton
    public static HandlerErrorFactory getInstance(){
        if(instance == null){
            instance = new HandlerErrorFactory();
        }
        return instance;
    }

    public HandlerSystemError getHandlerSystemError(){
        return HandlerSystemError.getInstance();
    }

    public HandlerUIError getHandlerUIError(){
        UIConfigurator uiconf = UIConfigurator.getInstance();
        UIType uiType = uiconf.getUI();

        return switch (uiType){
            case CLI ->  HandlerCLIError.getInstance();
            case JavaFxGUI ->  HandlerJavaFxGUIError.getInstance();
        };
    }
}

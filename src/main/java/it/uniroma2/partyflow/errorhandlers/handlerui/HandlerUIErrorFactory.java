package it.uniroma2.partyflow.errorhandlers.handlerui;

import it.uniroma2.partyflow.enums.UIType;
import it.uniroma2.partyflow.utilities.configurators.UIConfigurator;

import java.io.IOException;

/**
 * Its responsibility is to provide the appropriate UI error handler.
 */


public class HandlerUIErrorFactory {
    private static HandlerUIErrorFactory instance = null;

    private HandlerUIErrorFactory(){}

    // Guarantee Singleton
    public static HandlerUIErrorFactory getInstance(){
        if(instance == null){
            instance = new HandlerUIErrorFactory();
        }
        return instance;
    }

    public HandlerUIError getHandlerUIError(){
        try{
            UIConfigurator uiconf = UIConfigurator.getInstance();
            UIType uiType = uiconf.getUI();

            return switch (uiType){
                case CLI ->  HandlerCLIError.getInstance();
                case JavaFxGUI ->  HandlerJavaFxGUIError.getInstance();
            };
        }

        catch (IOException e){
            return HandlerJavaFxGUIError.getInstance();
        }
    }
}

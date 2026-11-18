package it.uniroma2.partyflow.errorhandlers.handlerui;

import it.uniroma2.partyflow.enums.UIType;
import it.uniroma2.partyflow.utilities.configurators.UIConfigurator;

import java.io.IOException;

/**
 * Factory responsible for providing the appropriate UI error handler.
 *
 * <p>Uses a factory method to encapsulate the selection of the concrete
 * error handler according to the user interface type specified by
 * UIConfigurator, without exposing concrete implementations to the caller.</p>
 *
 * <p>Implements the Singleton pattern to provide a single factory instance.
 * If the configuration cannot be loaded, the JavaFX GUI error handler
 * is used as the default.</p>
 */



public class HandlerUIErrorFactory {
    private static HandlerUIErrorFactory instance = null;

    private HandlerUIErrorFactory(){}

    // Ensures a single instance
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
                case JAVA_FX_GUI ->  HandlerJavaFxGUIError.getInstance();
            };
        }

        catch (IOException _){
            return HandlerJavaFxGUIError.getInstance();
        }
    }
}

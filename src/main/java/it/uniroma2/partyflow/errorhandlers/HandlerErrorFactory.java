package it.uniroma2.partyflow.errorhandlers;

import it.uniroma2.partyflow.enums.UIType;

public class HandlerErrorFactory {
    private static HandlerErrorFactory instance = null;

    private HandlerErrorFactory (){}

    public static HandlerErrorFactory getInstance(){
        if(instance == null){
            instance = new HandlerErrorFactory();
        }
        return instance;
    }

    public HandlerSystemError getHandlerSystemError(){
        return HandlerSystemError.getInstance();
    }

    public HandlerUIError getHandlerUIError(UIType uiType){
        return switch (uiType){
            case CLI ->  HandlerCLIError.getInstance();
            case GUI ->  HandlerGUIError.getInstance();
        };
    }
}

package it.uniroma2.partyflow.errorhandlers;

public class HandlerGUIError implements HandlerUIError{
    private static HandlerGUIError instance = null;

    private HandlerGUIError(){}

    public static HandlerGUIError getInstance(){
        if(instance == null){
            instance = new HandlerGUIError();
        }
        return instance;
    }
}

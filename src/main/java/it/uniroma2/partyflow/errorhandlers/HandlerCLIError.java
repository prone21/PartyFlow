package it.uniroma2.partyflow.errorhandlers;

public class HandlerCLIError implements HandlerUIError {
    static private HandlerCLIError instance = null;

    private HandlerCLIError(){}

    public static HandlerCLIError getInstance(){
        if(instance == null){
            instance = new HandlerCLIError();
        }
        return instance;
    }
}

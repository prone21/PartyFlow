package it.uniroma2.partyflow.exceptions;

public class EmptyException extends Exception{
    int type;
    public EmptyException (int type){
        super();
        this.type = type;
    }

    public int getType(){
        return this.type;
    }
}

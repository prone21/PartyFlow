package it.uniroma2.partyflow.exceptions;

public class SignUpException extends Exception {
    int type;
    public SignUpException (String message, int type){
        super(message);
        this.type = type;
    }

   /* public int getType(){
        return this.type;
    }
    */
}

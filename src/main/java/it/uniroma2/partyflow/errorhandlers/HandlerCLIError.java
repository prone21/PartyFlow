package it.uniroma2.partyflow.errorhandlers;


import it.uniroma2.partyflow.enums.UserField;

/**
 * This class handles the error that may occur in a CLI GUI.
 *
 */

public class HandlerCLIError implements HandlerUIError {
    private static HandlerCLIError instance = null;

    private HandlerCLIError(){}

    public static HandlerCLIError getInstance(){
        if(instance == null){
            instance = new HandlerCLIError();
        }
        return instance;
    }

    /*
     * Prints an error message to the command-line interface,
     * allowing the user to identify the reason for the failure.
     */
    public void printErrorMessage(String message) {
        System.out.println("Error: " + message);
    }

    /*
     * Prints an error message identifying the required field
     * that has not been provided by the user.
     */
    public void printRequiredFieldError(UserField field) {

        String fieldName = switch (field) {
            case NAME -> "Name";
            case SURNAME -> "Surname";
            case DATE_OF_BIRTH -> "Date of birth";
            case EMAIL -> "Email";
            case PASSWORD -> "Password";
            case GENDER -> "Gender";
            case ACCOUNT_TYPE -> "Account type";
        };

        System.out.println("Error: " + fieldName + " is required.");

        System.out.println();
        System.out.println("Please try again.");
        System.out.println();
    }
}

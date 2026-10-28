package it.uniroma2.partyflow.graphic_controller.signupgraphiccontroller;

import it.uniroma2.partyflow.app_controller.SignUpController;
import it.uniroma2.partyflow.beans.UserBean;
import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.enums.Gender;
import it.uniroma2.partyflow.errorhandlers.HandlerCLIError;
import it.uniroma2.partyflow.exceptions.RequiredFieldException;
import it.uniroma2.partyflow.exceptions.InvalidFieldFormatException;

import java.util.Scanner;

public class SignUpGraphicControllerCLI {

    private final Scanner scanner = new Scanner(System.in);

    public void execute() {

        System.out.println();
        System.out.println("=== PARTYFLOW - SIGN UP ===");
        this.createAccount();

    }

    private String readField(String field) {
        System.out.print(field+": ");
        return scanner.nextLine();
    }

    private void createAccount(){
        // 1) create the bean where the user date will be memorized
        UserBean signUpBean = new UserBean();

        while(true){
            try{
                signUpBean.setName(readField("Name"));
                signUpBean.setSurname(readField("Surname"));
                signUpBean.setDateOfBirth("Date of");


                signUpBean.setEmail("Email");
                signUpBean.setPassword("password");

                /*signUpBean.setGender();

                signUpBean.setAccountType(this.obtainSelectedAccountType());
                */
                 */
                //---------------------------------------------

            }
            catch (RequiredFieldException e){
                /*
                 * Handles missing required fields in the sign-up form.
                 * Uses a switch statement to identify the corresponding JavaFX element
                 * and delegates its highlighting to the JavaFxGUI error handler.
                 */

                HandlerCLIError handlerCLIError = HandlerCLIError.getInstance();

                /*
                switch (e.getField()){
                    case NAME->{
                    }
                    case SURNAME -> {
                    }
                    case DATE_OF_BIRTH -> {
                    }
                    case EMAIL -> {
                    }
                    case PASSWORD -> {
                    }
                    case GENDER -> {
                    }
                    case ACCOUNT_TYPE -> {
                    }
                }

                 */
            }

            catch (InvalidFieldFormatException e){
                /*
                 * Handles invalid field formats in the sign-up form.
                 * Delegates the display of the exception message
                 * to the JavaFX GUI error handler.
                 */
                HandlerCLIError handlerCLIError = HandlerCLIError.getInstance();

            }
        }


        // 2) pass the UserBean to the application controller
        SignUpController signUpController = new SignUpController();
        signUpController.createAccount(signUpBean);
        //---------------------------------------------

        System.out.println();
        System.out.println("Account created successfully.");
    }


    /*
    private String readName() {

        System.out.print("Name: ");
        return scanner.nextLine();
        /*
        while (true) {

            try {
                System.out.print("Name: ");
                credentials.setName(scanner.nextLine());
                return;

            } catch (RequiredFieldException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }


    }
}

private void readSurname() {
    System.out.print("Surname: ");
    return scanner.nextLine();

    /*
    while (true) {

        try {
            System.out.print("Surname: ");
            credentials.setSurname(scanner.nextLine());
            return;

        } catch (RequiredFieldException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

private void readDateOfBirth() {
    System.out.print("Date of birth (DD/MM/YYYY): ");
    return scanner.nextLine();
    /*
    while (true) {

        try {
            System.out.print("Date of birth (DD/MM/YYYY): ");
            credentials.setDateOfBirth(scanner.nextLine());
            return;

        } catch (RequiredFieldException | InvalidFieldFormatException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


}

private void readEmail(UserBean credentials) {
    System.out.print("Email: ");
    return scanner.nextLine();

    /*
    while (true) {

        try {
            System.out.print("Email: ");
            credentials.setEmail(scanner.nextLine());
            return;

        } catch (RequiredFieldException | InvalidFieldFormatException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}

private void readPassword(UserBean credentials) {

    while (true) {

        try {
            System.out.print("Password: ");
            credentials.setPassword(scanner.nextLine());
            return;

        } catch (RequiredFieldException | InvalidFieldFormatException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

private void readGender(UserBean credentials) {

    while (true) {

        try {
            System.out.println();
            System.out.println("Gender:");
            System.out.println("1 - Male");
            System.out.println("2 - Female");
            System.out.println("3 - Prefer not to say");

            System.out.print("Choice: ");

            String choice = scanner.nextLine();

            switch (choice) {

                case "1":
                    credentials.setGender(Gender.MALE);
                    return;

                case "2":
                    credentials.setGender(Gender.FEMALE);
                    return;

                case "3":
                    credentials.setGender(Gender.PNTS);
                    return;

                default:
                    System.out.println("Invalid choice.");
            }

        } catch (RequiredFieldException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

private void readAccountType(UserBean credentials) {

    while (true) {

        try {
            System.out.println();
            System.out.println("Account type:");
            System.out.println("1 - Participant");
            System.out.println("2 - Party Planner");

            System.out.print("Choice: ");

            String choice = scanner.nextLine();

            switch (choice) {

                case "1":
                    credentials.setAccountType(AccountType.Participant);
                    return;

                case "2":
                    credentials.setAccountType(AccountType.PartyPlanner);
                    return;

                default:
                    System.out.println("Invalid choice.");
            }

        } catch (RequiredFieldException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

     */
}
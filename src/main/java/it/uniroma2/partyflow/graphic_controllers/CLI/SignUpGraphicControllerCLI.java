package it.uniroma2.partyflow.graphic_controllers.CLI;

import it.uniroma2.partyflow.app_controllers.SignUpController;
import it.uniroma2.partyflow.beans.UserBean;
import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.enums.Gender;
import it.uniroma2.partyflow.errorhandlers.handlerui.HandlerCLIError;
import it.uniroma2.partyflow.exceptions.uiexception.ApplicationException;
import it.uniroma2.partyflow.exceptions.uiexception.InvalidFieldFormatException;
import it.uniroma2.partyflow.exceptions.uiexception.RequiredFieldException;
import it.uniroma2.partyflow.utilities.navigators.CLINavigatorSingleton;

import java.util.Scanner;

public class SignUpGraphicControllerCLI {

    private final Scanner scanner = new Scanner(System.in);

    public void execute() {
        while (true) {
            try {
                System.out.println();
                System.out.println("=== PARTYFLOW - SIGN UP ===");
                System.out.println("1 - Sign up");
                System.out.println("0 - Exit");

                String choice = this.readField("Choice");

                switch (choice) {
                    case "1" -> {
                        if(this.createAccount()){
                            return;
                        }
                    }
                    case "0" -> {
                        this.exit();
                        return;
                    }
                    default -> throw new ApplicationException("Invalid choice");
                }

            } catch (ApplicationException e) {
                HandlerCLIError handlerCLIError = HandlerCLIError.getInstance();
                handlerCLIError.errorMessage(e.getMessage());
            }
        }

    }

    private void exit(){
        System.out.println("Goodbye!");

    }

    private String readField(String field) {
        System.out.print(field + ": ");
        return scanner.nextLine();
    }

    private Gender obtainSelectedGender() {
        System.out.println();
        System.out.println("Gender:");
        System.out.println("1 - Male");
        System.out.println("2 - Female");
        System.out.println("3 - Prefer not to say");

        String choice = this.readField("Choice");

        return switch (choice) {
            case "1" -> Gender.MALE;
            case "2" -> Gender.FEMALE;
            case "3" -> Gender.PNTS;
            default -> null;
        };
    }

    private AccountType obtainSelectedAccountType() {
        System.out.println();
        System.out.println("Account type:");
        System.out.println("1 - Participant");
        System.out.println("2 - Party Planner");

        String choice = this.readField("Choice");

        return switch (choice) {
            case "1" -> AccountType.Participant;
            case "2" -> AccountType.PartyPlanner;
            default -> null;
        };
    }

    private boolean createAccount(){
        try{
            // 1) create the bean where the user date will be memorized
            UserBean signUpBean = new UserBean();

            signUpBean.setName(readField("Name"));
            signUpBean.setSurname(readField("Surname"));
            signUpBean.setDateOfBirth(
                    readField("Date of birth (DD/MM/YYYY)")
            );

            signUpBean.setEmail(readField("Email"));
            signUpBean.setPassword(readField("Password"));

            signUpBean.setGender(
                    this.obtainSelectedGender()
            );

            signUpBean.setAccountType(
                    this.obtainSelectedAccountType()
            );
            //---------------------------------------------

            // 2) Pass the UserBean to the application controller
            SignUpController signUpController =
                    new SignUpController();

            signUpController.createAccount(signUpBean);
            //---------------------------------------------

            // 3) show the right homepage
            CLINavigatorSingleton cliNavigatorSingleton = CLINavigatorSingleton.getInstance();
            cliNavigatorSingleton.goToHomePage(signUpBean.getAccountType());
            //---------------------------------------------

            return true;
        }

        catch (RequiredFieldException e) {
            /*
             * Handles missing required fields.
             * Delegates the error message to the CLI error handler.
             */

            HandlerCLIError handlerCLIError = HandlerCLIError.getInstance();
            handlerCLIError.printRequiredFieldError(e.getField());
            return false;
        }

        catch (InvalidFieldFormatException e) {
            /*
             * Handles invalid field formats.
             * Delegates the exception message to the CLI error handler.
             */
            HandlerCLIError handlerCLIError =
                    HandlerCLIError.getInstance();

            handlerCLIError.errorMessage(e.getMessage());

            System.out.println();
            System.out.println("Please try again.");
            System.out.println();
            return false;
        }

        catch (ApplicationException e){
            HandlerCLIError handlerCLIError = HandlerCLIError.getInstance();
            handlerCLIError.errorMessage(e.getMessage());
            return  false;
        }

    }
}


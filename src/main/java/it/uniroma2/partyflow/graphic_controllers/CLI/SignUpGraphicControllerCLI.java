package it.uniroma2.partyflow.graphic_controllers.CLI;

import it.uniroma2.partyflow.app_controllers.SignUpController;
import it.uniroma2.partyflow.beans.UserBean;
import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.enums.Gender;
import it.uniroma2.partyflow.errorhandlers.handlerui.HandlerCLIError;
import it.uniroma2.partyflow.exceptions.uiexception.ApplicationException;
import it.uniroma2.partyflow.exceptions.uiexception.InvalidFieldFormatException;
import it.uniroma2.partyflow.exceptions.uiexception.RequiredFieldException;
import it.uniroma2.partyflow.utilities.ConsolePrinter;
import it.uniroma2.partyflow.utilities.navigators.CLINavigatorSingleton;

import java.util.Scanner;

public class SignUpGraphicControllerCLI {
    private static final String SELECTION_CHOICE = "Choice";
    private final ConsolePrinter printer = ConsolePrinter.getInstance();
    private final Scanner scanner = new Scanner(System.in);

    public void execute() {
        while (true) {
            try {
                printer.println();
                printer.println("=== PARTYFLOW - SIGN UP ===");
                printer.println("1 - Sign up");
                printer.println("0 - Exit");

                String choice = this.readField(SELECTION_CHOICE);

                switch (choice) {
                    case "1" -> {
                        boolean accountCreated = this.createAccount();
                        if (accountCreated) {
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
        printer.println("Goodbye!");
    }

    private String readField(String field) {
        printer.println(field + ": ");
        return scanner.nextLine();
    }

    private Gender obtainSelectedGender() {
        printer.println();
        printer.println("Gender:");
        printer.println("1 - Male");
        printer.println("2 - Female");
        printer.println("3 - Prefer not to say");

        String choice = this.readField(SELECTION_CHOICE);

        return switch (choice) {
            case "1" -> Gender.MALE;
            case "2" -> Gender.FEMALE;
            case "3" -> Gender.PNTS;
            default -> null;
        };
    }

    private AccountType obtainSelectedAccountType() {
        printer.println();
        printer.println("Account type:");
        printer.println("1 - Participant");
        printer.println("2 - Party Planner");

        String choice = this.readField(SELECTION_CHOICE);

        return switch (choice) {
            case "1" -> AccountType.PARTICIPANT;
            case "2" -> AccountType.PARTY_PLANNER;
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

            printer.println();
            printer.println("Please try again.");
            printer.println();
            return false;
        }

        catch (ApplicationException e){
            HandlerCLIError handlerCLIError = HandlerCLIError.getInstance();
            handlerCLIError.errorMessage(e.getMessage());
            return  false;
        }

    }
}


package it.uniroma2.partyflow.graphic_controllers.cli;

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


/**
 * Graphic controller responsible for managing the CLI registration interface.
 *
 * <p>Displays the registration menu, collects user data, and populates
 * a UserBean whose setters perform syntactic checks.
 * Delegates account creation to SignUpController.</p>
 *
 * <p>Manages navigation to the appropriate home page after successful
 * registration and delegates the display of error messages
 * to HandlerCLIError.</p>
 */


public class SignUpGraphicControllerCLI {
    private static final String SELECTION_CHOICE = "Choice";
    private final ConsolePrinter printer = ConsolePrinter.getInstance();
    private final Scanner scanner = new Scanner(System.in);



    /**
     * Displays the CLI registration menu and processes the user's input.
     *
     * <p>Repeatedly displays the menu until the user successfully creates
     * an account or chooses to exit the menu.</p>
     *
     * <p>Delegates the display of error messages related to invalid
     * menu choices to HandlerCLIError.</p>
     */

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

    /**
     * Displays a farewell message before exiting the CLI registration menu.
     */

    private void exit(){
        printer.println("Goodbye!");
    }


    /**
     * Displays a prompt for the specified field and reads the user's input
     * from the standard input stream.
     *
     * @param field the name of the field to display
     * @return the value entered by the user
     */

    private String readField(String field) {
        printer.println(field + ": ");
        return scanner.nextLine();
    }


    /**
     * Displays the available gender options and maps the user's
     * selection to the corresponding Gender enum constant.
     *
     * @return the selected gender, or null if the choice is invalid
     */

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


    /**
     * Displays the available account types and maps the user's
     * selection to the corresponding AccountType enum constant.
     *
     * @return the selected account type, or null if the choice is invalid
     */

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


    /**
     * Handles the account creation request.
     *
     * <p>Collects user data and populates a UserBean,
     * whose setters perform syntactic checks.</p>
     *
     * <p>Delegates account creation to SignUpController and navigation
     * to the appropriate home page to CLINavigatorSingleton.</p>
     *
     * <p>Handles missing mandatory fields, invalid field formats,
     * and application errors by delegating the display of error
     * messages to HandlerCLIError.</p>
     *
     * @return true if account creation succeeds and home page execution
     * completes normally, false if a validation or application error occurs
     */

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


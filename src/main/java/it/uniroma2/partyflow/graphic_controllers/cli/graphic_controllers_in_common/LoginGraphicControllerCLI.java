
package it.uniroma2.partyflow.graphic_controllers.cli.graphic_controllers_in_common;

import it.uniroma2.partyflow.app_controllers.LoginController;
import it.uniroma2.partyflow.beans.LoginCredentialsBean;
import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.errorhandlers.handlerui.HandlerCLIError;
import it.uniroma2.partyflow.exceptions.uiexception.ApplicationException;
import it.uniroma2.partyflow.exceptions.uiexception.RequiredFieldException;
import it.uniroma2.partyflow.utilities.console.ConsolePrinter;
import it.uniroma2.partyflow.utilities.console.ConsoleReader;
import it.uniroma2.partyflow.utilities.navigators.CLINavigatorSingleton;


/**
 * Graphic controller responsible for managing the CLI login interface.
 *
 * <p>Displays the login menu, collects user credentials, and populates
 * a LoginCredentialsBean to perform syntactic checks.
 * Delegates authentication to LoginController.</p>
 *
 * <p>Manages navigation between CLI pages and delegates the display
 * of error messages to HandlerCLIError.</p>
 */

public class LoginGraphicControllerCLI {

    private final ConsolePrinter printer = ConsolePrinter.getInstance();
    private final ConsoleReader consoleReader = ConsoleReader.getInstance();

    /**
     * Displays the CLI login menu and processes the user's input.
     *
     * <p>Repeatedly displays the menu until the user successfully logs in,
     * navigates to the registration page, or chooses to exit the menu.</p>
     *
     * <p>Delegates the display of error messages related to invalid
     * menu choices to HandlerCLIError.</p>
     */

    public void execute() {
        while (true) {
            try {
                printer.println();
                printer.println("=== PARTYFLOW - LOGIN ===");
                printer.println("1 - Log in");
                printer.println("2 - Don't have an account? Sign up");
                printer.println("3 - Continue with Google");
                printer.println("4 - Continue with Facebook");
                printer.println("0 - Exit");

                String choice = this.readField("Choice");

                switch (choice) {
                    case "1" -> {
                        boolean statusSubmitCredentials = this.submitCredentials();
                        if (statusSubmitCredentials) {
                            return;
                        }
                    }

                    case "2" -> {
                        this.signUp();
                        return;
                    }

                    case "3" -> this.continueWithGoogle();

                    case "4" -> this.continueWithFacebook();

                    case "0" -> {
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
     * Displays a message informing the user that authentication
     * through Google has not been implemented yet.
     */

    private void continueWithGoogle() {
        printer.println("Google log-in is not implemented yet.");
    }

    /**
     * Displays a message informing the user that authentication
     * through Facebook has not been implemented yet.
     */

    private void continueWithFacebook() {
        printer.println("Facebook log-in is not implemented yet.");
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
        return consoleReader.readLine();
    }


    /**
     * Displays a password field prompt and reads the user's password,
     * hiding the entered characters when a system console is available.
     *
     * @return the password entered by the user
     */
    private String readPasswordField() {
        printer.println("Password: ");
        return consoleReader.readPassword();
    }



    /**
     * Instantiates and executes the CLI sign-up graphic controller
     * to start the registration process.
     */

    private void signUp() {
        SignUpGraphicControllerCLI signUpController =
                new SignUpGraphicControllerCLI();
        printer.println();
        signUpController.execute();
    }

    /**
     * Handles the submission of user login credentials.
     *
     * <p>Collects the user's credentials and populates a
     * LoginCredentialsBean, whose setters perform syntactic checks.</p>
     *
     * <p>Delegates authentication to LoginController and navigation
     * to the appropriate home page to CLINavigatorSingleton.</p>
     *
     * <p>Handles missing mandatory fields and authentication errors
     * by delegating the display of error messages to HandlerCLIError.</p>
     *
     * @return true if authentication succeeds and home page execution
     * completes normally, false if a required field or application error occurs
     */


    private boolean submitCredentials() {

        try {
            // 1) Create and populate the credentials Bean
            LoginCredentialsBean loginBean =
                    new LoginCredentialsBean(this.readField("Email"),this.readPasswordField());
            //---------------------------------------------

            // 2) Pass the Bean to the application controller

            LoginController loginController =
                    new LoginController();

            AccountType accountType =
                    loginController.login(loginBean);
            //---------------------------------------------


            // 3) show the right homepage
            CLINavigatorSingleton cliNavigatorSingleton = CLINavigatorSingleton.getInstance();
            cliNavigatorSingleton.goToHomePage(accountType);
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

        catch (ApplicationException e){
            HandlerCLIError handlerCLIError = HandlerCLIError.getInstance();
            handlerCLIError.errorMessage(e.getMessage());
            return false;
        }

    }
}


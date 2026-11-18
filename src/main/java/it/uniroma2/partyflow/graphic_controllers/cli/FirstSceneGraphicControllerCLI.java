package it.uniroma2.partyflow.graphic_controllers.cli;

import it.uniroma2.partyflow.errorhandlers.handlerui.HandlerCLIError;
import it.uniroma2.partyflow.exceptions.uiexception.ApplicationException;
import it.uniroma2.partyflow.utilities.console.ConsolePrinter;
import it.uniroma2.partyflow.utilities.console.ConsoleReader;


/**
 * Graphic controller responsible for managing the initial CLI menu.
 *
 * <p>Displays the available options, retrieves the user's input,
 * and delegates the execution of login and registration operations
 * to the corresponding graphic controllers.</p>
 *
 * <p>Handles invalid menu choices and allows the user to exit
 * the application.</p>
 */


public class FirstSceneGraphicControllerCLI {

    private final ConsoleReader consoleReader = ConsoleReader.getInstance();
    private final ConsolePrinter printer = ConsolePrinter.getInstance();


    /**
     * Displays the initial CLI menu and processes the user's input.
     *
     * <p>Repeatedly displays the menu and executes the operation
     * corresponding to the selected option until the user chooses to exit.</p>
     *
     * <p>Delegates the display of error messages related to invalid
     * menu choices to HandlerCLIError.</p>
     */

    public void execute() {

        while (true) {
            try {
                printer.println();
                printer.println("===== PARTYFLOW =====");
                printer.println("1 - Log in");
                printer.println("2 - Sign up");
                printer.println("0 - Exit");
                printer.println("Choice: ");

                String choice = consoleReader.readLine();

                switch (choice) {

                    case "1":
                        this.login();
                        break;

                    case "2":
                        this.signUp();
                        break;

                    case "0":
                        this.exit();
                        return;

                    default:
                        throw new ApplicationException("Invalid choice");

                }
            }

            catch (ApplicationException e){
                HandlerCLIError handlerCLIError = HandlerCLIError.getInstance();
                handlerCLIError.errorMessage(e.getMessage());
            }
        }
    }


    /**
     * Instantiates and executes the CLI login graphic controller
     * to start the authentication process.
     */

    private void login(){
        LoginGraphicControllerCLI loginController =
                new LoginGraphicControllerCLI();

        loginController.execute();
    }


    /**
     * Instantiates and executes the CLI sign-up graphic controller
     * to start the registration process.
     */

    private void signUp(){
        SignUpGraphicControllerCLI signUpController =
                new SignUpGraphicControllerCLI();

        signUpController.execute();
    }


    /**
     * Displays a farewell message before exiting the initial CLI menu.
     */

    private void exit(){
        printer.println("Goodbye!");

    }

}
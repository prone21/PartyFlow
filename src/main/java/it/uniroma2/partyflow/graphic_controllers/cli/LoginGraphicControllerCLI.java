
package it.uniroma2.partyflow.graphic_controllers.cli;

import it.uniroma2.partyflow.app_controllers.LoginController;
import it.uniroma2.partyflow.beans.LoginCredentialsBean;
import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.errorhandlers.handlerui.HandlerCLIError;
import it.uniroma2.partyflow.exceptions.uiexception.ApplicationException;
import it.uniroma2.partyflow.exceptions.uiexception.RequiredFieldException;
import it.uniroma2.partyflow.utilities.ConsolePrinter;
import it.uniroma2.partyflow.utilities.navigators.CLINavigatorSingleton;

import java.util.Scanner;

public class LoginGraphicControllerCLI {

    private final Scanner scanner = new Scanner(System.in);
    private final ConsolePrinter printer = ConsolePrinter.getInstance();

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


    private void continueWithGoogle() {
        printer.println("Google log-in is not implemented yet.");
    }

    private void continueWithFacebook() {
        printer.println("Facebook log-in is not implemented yet.");
    }

    private String readField(String field) {
        printer.println(field + ": ");
        return scanner.nextLine();
    }

    private void signUp() {
        SignUpGraphicControllerCLI signUpController =
                new SignUpGraphicControllerCLI();
        printer.println();
        signUpController.execute();
    }

    private void exit(){
        printer.println("Goodbye!");

    }

    private boolean submitCredentials() {

        try {
            // 1) Create and populate the credentials Bean
            LoginCredentialsBean loginBean =
                    new LoginCredentialsBean();

            loginBean.setEmail(this.readField("Email"));
            loginBean.setPassword(this.readField("Password"));
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



package it.uniroma2.partyflow.graphic_controllers.CLI;

import it.uniroma2.partyflow.app_controllers.LoginController;
import it.uniroma2.partyflow.beans.LoginCredentialsBean;
import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.errorhandlers.HandlerCLIError;
import it.uniroma2.partyflow.exceptions.uiexception.ApplicationException;
import it.uniroma2.partyflow.exceptions.uiexception.RequiredFieldException;

import java.util.Scanner;

public class LoginGraphicControllerCLI {

    private final Scanner scanner = new Scanner(System.in);

    public void execute() {

        while (true) {
            try {
                System.out.println();
                System.out.println("=== PARTYFLOW - LOGIN ===");
                System.out.println("1 - Log in");
                System.out.println("2 - Don't have an account? Sign up");
                System.out.println("3 - Continue with Google");
                System.out.println("4 - Continue with Facebook");
                System.out.println("0 - Exit");

                String choice = this.readField("Choice");

                switch (choice) {
                    case "1" -> {
                        if (this.submitCredentials()) {
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
        System.out.println("Google log-in is not implemented yet.");
    }

    private void continueWithFacebook() {
        System.out.println("Facebook log-in is not implemented yet.");
    }

    private String readField(String field) {
        System.out.print(field + ": ");
        return scanner.nextLine();
    }

    private void signUp() {
        SignUpGraphicControllerCLI signUpController =
                new SignUpGraphicControllerCLI();
        System.out.println();
        signUpController.execute();
    }

    private void exit(){
        System.out.println("Goodbye!");

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
            switch (accountType) {
                case Participant -> {
                    PHomePageGraphicControllerCLI homePage = new PHomePageGraphicControllerCLI();
                    System.out.println();
                    homePage.execute();
                }

                case PartyPlanner -> {
                    PpHomePageGraphicControllerCLI homePage = new PpHomePageGraphicControllerCLI();
                    System.out.println();
                    homePage.execute();
                }

            }
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


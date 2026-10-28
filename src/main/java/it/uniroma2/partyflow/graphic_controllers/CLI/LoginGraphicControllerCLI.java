
package it.uniroma2.partyflow.graphic_controllers.CLI;

import it.uniroma2.partyflow.app_controllers.LoginappController;
import it.uniroma2.partyflow.beans.LoginCredentialsBean;
import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.errorhandlers.HandlerCLIError;
import it.uniroma2.partyflow.errorhandlers.HandlerSystemError;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Scanner;

public class LoginGraphicControllerCLI {

    private final Scanner scanner = new Scanner(System.in);

    public void execute() {

        while (true) {
            System.out.println();
            System.out.println("=== PARTYFLOW - LOGIN ===");
            System.out.println("1 - Log in");
            System.out.println("2 - Sign up");
            System.out.println("3 - Continue with Google");
            System.out.println("4 - Continue with Facebook");
            System.out.println("0 - Go backward");

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
                    this.goBackward();
                    return;
                }

                default -> this.invalidChoice();
            }
        }
    }

    private void invalidChoice(){
        System.out.println("Invalid choice.");
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

        signUpController.execute();
    }

    private void goBackward() {
        /*
         * The calling controller manages the previous screen.
         * Returning from execute() restores its execution flow.
         */
    }

    private boolean submitCredentials() {

        try {
            // 1) Create and populate the credentials Bean
            LoginCredentialsBean loginBean =
                    new LoginCredentialsBean();

            loginBean.setEmail(this.readField("Email"));
            loginBean.setPwd(this.readField("Password"));
            //---------------------------------------------
            // 2) Pass the Bean to the application controller

            LoginappController loginController =
                    new LoginappController();

            AccountType accountType =
                    loginController.login(loginBean);
            //---------------------------------------------

            switch (accountType) {

                case Participant -> {
                    PHomePageGraphicControllerCLI homePage = new PHomePageGraphicControllerCLI();
                    System.out.println();
                    homePage.execute();
                    return true;
                }

                case PartyPlanner -> {
                    PpHomePageGraphicControllerCLI homePage = new PpHomePageGraphicControllerCLI();
                    System.out.println();
                    homePage.execute();
                    return true;
                }

            }

        } catch (SQLException | NoSuchAlgorithmException e) {
            /*
             * Delegates system error logging to the system handler
             * and user-facing error presentation to the CLI handler.
             */
            HandlerSystemError.getInstance().logError(e);

            HandlerCLIError.getInstance().printErrorMessage(
                    "An error occurred during login."
            );

            return false;
        }
    }


}

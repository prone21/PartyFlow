package it.uniroma2.partyflow.graphic_controllers.CLI;

import it.uniroma2.partyflow.errorhandlers.handlerui.HandlerCLIError;
import it.uniroma2.partyflow.exceptions.uiexception.ApplicationException;

import java.util.Scanner;

public class FirstSceneGraphicControllerCLI {

    private final Scanner scanner = new Scanner(System.in);


    public void execute() {

        while (true) {
            try {
                System.out.println();
                System.out.println("===== PARTYFLOW =====");
                System.out.println("1 - Log in");
                System.out.println("2 - Sign up");
                System.out.println("0 - Exit");
                System.out.print("Choice: ");

                String choice = this.scanner.nextLine();

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

    private void login(){
        LoginGraphicControllerCLI loginController =
                new LoginGraphicControllerCLI();

        loginController.execute();
    }


    private void signUp(){
        SignUpGraphicControllerCLI signUpController =
                new SignUpGraphicControllerCLI();

        signUpController.execute();
    }

    private void exit(){
        System.out.println("Goodbye!");

    }

}
package it.uniroma2.partyflow.graphic_controllers.CLI;

import it.uniroma2.partyflow.errorhandlers.handlerui.HandlerCLIError;
import it.uniroma2.partyflow.exceptions.uiexception.ApplicationException;
import it.uniroma2.partyflow.utilities.ConsolePrinter;

import java.util.Scanner;


public class FirstSceneGraphicControllerCLI {

    private final Scanner scanner = new Scanner(System.in);
    private final ConsolePrinter printer = ConsolePrinter.getInstance();

    public void execute() {

        while (true) {
            try {
                printer.println();
                printer.println("===== PARTYFLOW =====");
                printer.println("1 - Log in");
                printer.println("2 - Sign up");
                printer.println("0 - Exit");
                printer.println("Choice: ");

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
        printer.println("Goodbye!");

    }

}
package it.uniroma2.partyflow.graphic_controllers.CLI;

import java.util.Scanner;

public class FirstSceneGraphicControllerCLI {

    private final Scanner scanner = new Scanner(System.in);

    public void execute() {

        while (true) {

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
                    this.invalidChoice();
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

    private void invalidChoice(){
        System.out.println("Invalid choice.");
    }
}
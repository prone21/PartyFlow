package it.uniroma2.partyflow.graphic_controller.firstscenegraphiccontroller;

import it.uniroma2.partyflow.graphic_controller.logingraphiccontroller.LoginGraphicControllerCLI;
import it.uniroma2.partyflow.graphic_controller.signupgraphiccontroller.SignUpGraphicControllerCLI;

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

            String choice = scanner.nextLine();

            switch (choice) {

                case "1":
                    LoginGraphicControllerCLI loginController =
                            new LoginGraphicControllerCLI();

                    loginController.execute();
                    break;

                case "2":
                    SignUpGraphicControllerCLI signUpController =
                            new SignUpGraphicControllerCLI();

                    signUpController.execute();
                    break;

                case "0":
                    System.out.println("Goodbye!");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
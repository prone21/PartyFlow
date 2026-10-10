package it.uniroma2.partyflow.graphic_controller.logingraphiccontroller;

import it.uniroma2.partyflow.app_controller.LoginappController;
import it.uniroma2.partyflow.beans.LoginCredentialsBean;
import it.uniroma2.partyflow.enums.AccountType;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Scanner;

public class LoginGraphicControllerCLI {

    private final Scanner scanner = new Scanner(System.in);
    private final LoginappController loginController = new LoginappController();

    public void execute() {

        System.out.println();
        System.out.println("=== PARTYFLOW - LOGIN ===");

        while (true) {

            String email = readEmail();
            String password = readPassword();

            LoginCredentialsBean loginBean = new LoginCredentialsBean();

            loginBean.setEmail(email);
            loginBean.setPwd(password);

            try {

                AccountType accountType =
                        loginController.login(loginBean);

                switch (accountType) {

                    case Participant:
                        System.out.println();
                        System.out.println("Login successful.");
                        System.out.println("Logged in as Participant.");
                        return;

                    case PartyPlanner:
                        System.out.println();
                        System.out.println("Login successful.");
                        System.out.println("Logged in as Party Planner.");
                        return;

                    case AccountNotExsist:
                        System.out.println();
                        System.out.println("Invalid email or password.");
                        System.out.println("Please try again.");
                        break;
                }

            } catch (SQLException e) {

                System.out.println(
                        "Database error while performing login."
                );
                return;

            } catch (NoSuchAlgorithmException e) {

                System.out.println(
                        "Internal error while performing login."
                );
                return;
            }
        }
    }

    private String readEmail() {

        while (true) {

            System.out.print("Email: ");
            String email = scanner.nextLine();

            if (!email.isBlank()) {
                return email;
            }

            System.out.println("Email cannot be empty.");
        }
    }

    private String readPassword() {

        while (true) {

            System.out.print("Password: ");
            String password = scanner.nextLine();

            if (!password.isBlank()) {
                return password;
            }

            System.out.println("Password cannot be empty.");
        }
    }
}
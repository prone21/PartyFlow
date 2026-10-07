package it.uniroma2.partyflow.graphic_controller.signupgraphiccontroller;

import it.uniroma2.partyflow.app_controller.SignUpController;
import it.uniroma2.partyflow.beans.CredentialsBean;
import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.enums.Gender;
import it.uniroma2.partyflow.exceptions.EmptyException;
import it.uniroma2.partyflow.exceptions.SignUpException;

import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class SignUpGraphicControllerCLI {

    private final Scanner scanner = new Scanner(System.in);
    private final SignUpController signUpController = new SignUpController();

    public void execute() {

        System.out.println();
        System.out.println("=== PARTYFLOW - SIGN UP ===");

        CredentialsBean credentials = new CredentialsBean();

        readName(credentials);
        readSurname(credentials);
        readDateOfBirth(credentials);
        readEmail(credentials);
        readPassword(credentials);
        readGender(credentials);
        readAccountType(credentials);

        try {
            signUpController.createAccount(credentials);

            System.out.println();
            System.out.println("Account created successfully.");

        } catch (NoSuchAlgorithmException e) {
            System.out.println("Internal error while creating the account.");
        }
    }

    private void readName(CredentialsBean credentials) {

        while (true) {

            try {
                System.out.print("Name: ");
                credentials.setName(scanner.nextLine());
                return;

            } catch (EmptyException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void readSurname(CredentialsBean credentials) {

        while (true) {

            try {
                System.out.print("Surname: ");
                credentials.setSurname(scanner.nextLine());
                return;

            } catch (EmptyException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void readDateOfBirth(CredentialsBean credentials) {

        while (true) {

            try {
                System.out.print("Date of birth (DD/MM/YYYY): ");
                credentials.setDateOfBirth(scanner.nextLine());
                return;

            } catch (EmptyException | SignUpException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void readEmail(CredentialsBean credentials) {

        while (true) {

            try {
                System.out.print("Email: ");
                credentials.setEmail(scanner.nextLine());
                return;

            } catch (EmptyException | SignUpException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void readPassword(CredentialsBean credentials) {

        while (true) {

            try {
                System.out.print("Password: ");
                credentials.setPassword(scanner.nextLine());
                return;

            } catch (EmptyException | SignUpException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void readGender(CredentialsBean credentials) {

        while (true) {

            try {
                System.out.println();
                System.out.println("Gender:");
                System.out.println("1 - Male");
                System.out.println("2 - Female");
                System.out.println("3 - Prefer not to say");

                System.out.print("Choice: ");

                String choice = scanner.nextLine();

                switch (choice) {

                    case "1":
                        credentials.setGender(Gender.MALE);
                        return;

                    case "2":
                        credentials.setGender(Gender.FEMALE);
                        return;

                    case "3":
                        credentials.setGender(Gender.PNTS);
                        return;

                    default:
                        System.out.println("Invalid choice.");
                }

            } catch (EmptyException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void readAccountType(CredentialsBean credentials) {

        while (true) {

            try {
                System.out.println();
                System.out.println("Account type:");
                System.out.println("1 - Participant");
                System.out.println("2 - Party Planner");

                System.out.print("Choice: ");

                String choice = scanner.nextLine();

                switch (choice) {

                    case "1":
                        credentials.setAccountType(AccountType.Participant);
                        return;

                    case "2":
                        credentials.setAccountType(AccountType.PartyPlanner);
                        return;

                    default:
                        System.out.println("Invalid choice.");
                }

            } catch (EmptyException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
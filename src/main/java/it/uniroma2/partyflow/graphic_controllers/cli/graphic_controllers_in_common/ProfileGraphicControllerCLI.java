package it.uniroma2.partyflow.graphic_controllers.cli.graphic_controllers_in_common;

import it.uniroma2.partyflow.app_controllers.ProfileController;
import it.uniroma2.partyflow.beans.UserBean;
import it.uniroma2.partyflow.errorhandlers.HandlerSystemError;
import it.uniroma2.partyflow.errorhandlers.handlerui.HandlerCLIError;
import it.uniroma2.partyflow.exceptions.uiexception.ApplicationException;
import it.uniroma2.partyflow.exceptions.uiexception.InvalidFieldFormatException;
import it.uniroma2.partyflow.exceptions.uiexception.RequiredFieldException;
import it.uniroma2.partyflow.utilities.console.ConsolePrinter;
import it.uniroma2.partyflow.utilities.console.ConsoleReader;

/**
 * Graphic controller responsible for managing the CLI user profile.
 *
 * <p>Retrieves and displays the authenticated user's personal information
 * through ProfileController.</p>
 *
 * <p>Handles user input and navigation between CLI menus,
 * and delegates error reporting
 * to HandlerCLIError.</p>
 */

public class ProfileGraphicControllerCLI {
    private final ConsolePrinter printer = ConsolePrinter.getInstance();
    private final ConsoleReader consoleReader = ConsoleReader.getInstance();

    private final ProfileController profileController = new ProfileController();

    /**
     * Displays the user profile and processes the user's input.
     *
     * <p>Initially displays the authenticated user's personal information,
     * then repeatedly presents the available profile-related operations.</p>
     *
     * <p>Handles profile editing, password changes, and navigation
     * back to the previous menu. Invalid menu choices are handled
     * through HandlerCLIError.</p>
     *
     * <p>When the user logs out, the session is invalidated and
     * the logout result is propagated to the calling controller.</p>
     *
     * @return true if the user logs out, false if the user
     *         chooses to return to the previous menu
     */
    public boolean execute() {
        // -------show my profile information------
        printer.println();
        printer.println("=== PARTYFLOW - MY PROFILE ===");
        this.showProfile();
        // ----------------------------------------

        while (true) {
            try {
                printer.println();
                printer.println("1 - Edit Profile");
                printer.println("2 - Change Password");
                printer.println("3 - Log out");
                printer.println("4 - Show Profile");
                printer.println("0 - Back");

                printer.println("Choice: ");
                String choice = consoleReader.readLine();

                switch (choice) {
                    case "1" -> this.editProfile();

                    case "2" -> this.changePassword();

                    case "3" -> {
                        // go to FirstPageCli
                        this.logOut();
                        return true;
                    }

                    case "4" -> this.showProfile();

                    case "0" -> {
                        // go back
                        return false;
                    }

                    default -> throw new ApplicationException("Invalid choice");
                }
            }

            catch (ApplicationException e) {
                HandlerCLIError handlerCLIError = HandlerCLIError.getInstance();
                handlerCLIError.errorMessage(e.getMessage());
            }
        }
    }

    /**
     * Retrieves and displays the authenticated user's personal information.
     *
     * <p>Delegates profile retrieval to ProfileController and prints
     * the user's name, surname, email, date of birth, gender,
     * and account type.</p>
     *
     * <p>Handles application errors through HandlerCLIError.
     * Invalid or missing data retrieved from the persistence layer
     * is logged through HandlerSystemError, while a generic error
     * message is displayed to the user.</p>
     */
    private void showProfile()  {
        try{
            UserBean user = this.profileController.retriveProfile();

            printer.println();
            printer.println("--- PERSONAL INFORMATION ---");
            printer.println("Name: " + user.getName());
            printer.println("Surname: " + user.getSurname());
            printer.println("Email: " + user.getEmail());
            printer.println("Date of birth: " + user.getDateOfBirth());
            printer.println("Gender: " + user.getGender());
            printer.println("Account type: " + user.getAccountType());
        }
        catch (ApplicationException e) {
            HandlerCLIError handlerCLIError = HandlerCLIError.getInstance();
            handlerCLIError.errorMessage(e.getMessage());
        }
        catch (RequiredFieldException | InvalidFieldFormatException e) {
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(
                    "Failed to load profile due to invalid user data: "
                            + e.getMessage(), e
            );
            HandlerCLIError handlerCLIError = HandlerCLIError.getInstance();
            handlerCLIError.errorMessage(
                    "We couldn't load your profile due to an unexpected error. " +
                            "Please try again later.");
        }

    }

    /**
     * Handles the user's request to edit their profile.
     */
    private void editProfile() {
        printer.println("'Edit Profile' feature is not implemented yet.");
    }

    /**
     * Handles the user's request to change their password.
     */
    private void changePassword() {
        printer.println("'Change Password' feature is not implemented yet.");
    }


    /**
     * Logs out the currently authenticated user.
     *
     * <p>Delegates session invalidation to ProfileController.
     * The calling execute() method propagates the logout result
     * to the previous CLI controller, allowing navigation
     * back to the initial menu.</p>
     */
    private void logOut() {
        this.profileController.logOut();
        printer.println();
    }
}


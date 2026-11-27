package it.uniroma2.partyflow.graphic_controllers.cli.pp_graphic_controllers;

import it.uniroma2.partyflow.errorhandlers.handlerui.HandlerCLIError;
import it.uniroma2.partyflow.exceptions.uiexception.ApplicationException;
import it.uniroma2.partyflow.graphic_controllers.cli.graphic_controllers_in_common.MyNotificationsGraphicControllerCLI;
import it.uniroma2.partyflow.graphic_controllers.cli.graphic_controllers_in_common.ProfileGraphicControllerCLI;
import it.uniroma2.partyflow.utilities.console.ConsolePrinter;
import it.uniroma2.partyflow.utilities.console.ConsoleReader;


/**
 * Graphic controller responsible for managing the Party Planner CLI home page.
 *
 * <p>Displays the available Party Planner operations</p>
 *
 * <p>Manages navigation between CLI menus by instantiating
 * and executing the corresponding graphic controllers</p>
 *
 * <p>Handles invalid menu choices through HandlerCLIError.</p>
 */


public class PpHomePageGraphicControllerCLI {
    private final ConsolePrinter printer = ConsolePrinter.getInstance();
    private final ConsoleReader consoleReader = ConsoleReader.getInstance();

    /**
     * Displays the Party Planner home menu and processes user input.
     *
     * <p>Repeatedly displays the available operations and delegates
     * the execution of the selected functionality to the corresponding method.</p>
     *
     * <p>The menu remains active until the user logs out from the profile page.
     * The logout result is received through myProfile(), causing the loop
     * to terminate and returning control to the calling CLI controller.</p>
     *
     * <p>Handles invalid menu choices by throwing an ApplicationException
     * and displaying the error message through HandlerCLIError.</p>
     */
    public void execute() {
        boolean loggedOut = false;

        while (!loggedOut) {
            try {
                printer.println();
                printer.println("=== PARTYFLOW - PARTY PLANNER HOME ===");
                // -------------- main fun -------------------
                printer.println("1 - Create Hangout");
                printer.println("2 - My Events");
                printer.println("3 - Create Public Event");
                // -------------------------------------------

                // ------------- default fun -----------------
                printer.println("4 - My Notifications");
                printer.println("5 - My Profile");
                // -------------------------------------------


                printer.println("Choice: ");
                String choice = consoleReader.readLine();

                switch (choice) {
                    case "1" -> this.createHangout();
                    case "2" -> this.myEvents();
                    case "3" -> this.createPublicEvent();
                    case "4" -> this.myNotifications();
                    case "5" -> loggedOut = this.myProfile();

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
     * Handles the user's request to create a new hangout.
     */
    private void createHangout() {
        printer.println(
                "'Create Hangout' feature is not implemented yet."
        );
    }

    /**
     * Navigates to the My Events CLI page.
     *
     * <p>Instantiates and executes MyEventsGraphicControllerCLI,
     * allowing the Party Planner to access their events.</p>
     */
    private void myEvents() {
        MyEventsGraphicControllerCLI myEventsGraphicControllerCLI = new MyEventsGraphicControllerCLI();
        printer.println();
        myEventsGraphicControllerCLI.execute();
    }

    /**
     * Handles the user's request to create a new public event.
     */
    private void createPublicEvent() {
        printer.println(
                "'Create Public Event' feature is not implemented yet."
        );
    }

    /**
     * Navigates to the My Notifications CLI page.
     *
     * <p>Instantiates and executes MyNotificationsGraphicControllerCLI
     * to display the user's notifications interface.</p>
     */
    private void myNotifications() {
        MyNotificationsGraphicControllerCLI myNotificationsGraphicControllerCLI = new MyNotificationsGraphicControllerCLI();
        printer.println();
        myNotificationsGraphicControllerCLI.execute();
    }

    /**
     * Navigates to the My Profile CLI page.
     *
     * <p>Instantiates and executes ProfileGraphicControllerCLI,
     * allowing the authenticated Party Planner to access
     * their personal information and profile-related operations.</p>
     *
     * <p>Returns the logout status received from the profile controller,
     * allowing the calling method to determine whether to terminate
     * the Party Planner home menu or continue its execution.</p>
     *
     * @return true if the user logs out, false if the user
     *         returns to the Party Planner home menu
     */
    private boolean myProfile() {
        ProfileGraphicControllerCLI profileGraphicControllerCLI = new ProfileGraphicControllerCLI();
        printer.println();
        return profileGraphicControllerCLI.execute();
    }

}

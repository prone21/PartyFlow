package it.uniroma2.partyflow.utilities.navigators;

import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.graphic_controllers.cli.PHomePageGraphicControllerCLI;
import it.uniroma2.partyflow.graphic_controllers.cli.PpHomePageGraphicControllerCLI;
import it.uniroma2.partyflow.utilities.ConsolePrinter;

/**
 * Singleton responsible for managing navigation between CLI pages.
 *
 */

public class CLINavigatorSingleton {
    private static CLINavigatorSingleton instance;
    private final ConsolePrinter printer = ConsolePrinter.getInstance();
    private CLINavigatorSingleton(){}

    // guarantee singleton
    public static CLINavigatorSingleton getInstance(){
        if(instance == null){
            instance = new CLINavigatorSingleton();
        }
        return instance;
    }

    /**
     * Navigates to the appropriate CLI home page based on the user's account type.
     *
     * <p>Instantiates and executes the corresponding graphic controller
     * for either a Participant or a Party Planner.</p>
     *
     * @param accountType the account type used to determine the destination page
     */

    public void goToHomePage(AccountType accountType){
        switch (accountType) {
            case PARTICIPANT -> {
                PHomePageGraphicControllerCLI homePage = new PHomePageGraphicControllerCLI();
                printer.println();
                homePage.execute();
            }

            case PARTY_PLANNER -> {
                PpHomePageGraphicControllerCLI homePage = new PpHomePageGraphicControllerCLI();
                printer.println();
                homePage.execute();
            }

        }
    }
}

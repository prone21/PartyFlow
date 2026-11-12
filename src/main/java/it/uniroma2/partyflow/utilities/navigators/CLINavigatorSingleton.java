package it.uniroma2.partyflow.utilities.navigators;

import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.graphic_controllers.CLI.PHomePageGraphicControllerCLI;
import it.uniroma2.partyflow.graphic_controllers.CLI.PpHomePageGraphicControllerCLI;


/**
 * Singleton responsible for managing navigation between CLI "pages".
 */

public class CLINavigatorSingleton {
    private static CLINavigatorSingleton instance;

    private CLINavigatorSingleton(){}

    // guarantee singleton
    public static CLINavigatorSingleton getInstance(){
        if(instance == null){
            instance = new CLINavigatorSingleton();
        }
        return instance;
    }

    public void goToHomePage(AccountType accountType){
        switch (accountType) {
            case Participant -> {
                PHomePageGraphicControllerCLI homePage = new PHomePageGraphicControllerCLI();
                System.out.println();
                homePage.execute();
            }

            case PartyPlanner -> {
                PpHomePageGraphicControllerCLI homePage = new PpHomePageGraphicControllerCLI();
                System.out.println();
                homePage.execute();
            }

        }
    }
}

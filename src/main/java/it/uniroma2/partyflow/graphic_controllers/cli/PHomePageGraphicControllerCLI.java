package it.uniroma2.partyflow.graphic_controllers.cli;

import it.uniroma2.partyflow.utilities.console.ConsolePrinter;

public class PHomePageGraphicControllerCLI {
    private final ConsolePrinter printer = ConsolePrinter.getInstance();

    public void execute(){
        printer.println("Login successful.");
        printer.println("Logged in as Participant.");
    }
}

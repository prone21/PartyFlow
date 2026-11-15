package it.uniroma2.partyflow.graphic_controllers.cli;

import it.uniroma2.partyflow.utilities.ConsolePrinter;

public class PpHomePageGraphicControllerCLI {
    private final ConsolePrinter printer = ConsolePrinter.getInstance();

    public void execute(){
        printer.println("Login successful.");
        printer.println("Logged in as Party Planner.");
    }
}

package it.uniroma2.partyflow.launcher;

import it.uniroma2.partyflow.graphic_controller.firstscenegraphiccontroller.FirstSceneGraphicControllerCLI;

public class CliApplication {

    public void start() {

        FirstSceneGraphicControllerCLI controller =
                new FirstSceneGraphicControllerCLI();

        controller.execute();
    }
}
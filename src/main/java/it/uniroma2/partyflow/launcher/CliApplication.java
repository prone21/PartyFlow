package it.uniroma2.partyflow.launcher;

import it.uniroma2.partyflow.graphic_controller.firstscenegraphiccontroller.FirstSceneGraphicControllerCLI;

public class CliApplication implements UIApplication {
    public void start() {

        FirstSceneGraphicControllerCLI controller =
                new FirstSceneGraphicControllerCLI();

        controller.execute();
    }

    public void launch(){
        this.start();
    }
}
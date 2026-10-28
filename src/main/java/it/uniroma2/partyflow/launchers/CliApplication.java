package it.uniroma2.partyflow.launchers;

import it.uniroma2.partyflow.graphic_controllers.CLI.FirstSceneGraphicControllerCLI;

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
package it.uniroma2.partyflow;
import it.uniroma2.partyflow.launcher.UIApplication;
import it.uniroma2.partyflow.launcher.UIApplicationFactory;

public class Main {
    public static void main() {
        launchInterface();
    }

    private static void launchInterface(){
        UIApplicationFactory uaf = UIApplicationFactory.getInstance();
        UIApplication uia = uaf.getUIApplication();
        uia.launch();
    }



}
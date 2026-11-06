module it.uniroma2.partyflow {

    // Without this, I can't use the classes defined in these module.
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires com.opencsv;
    requires jdk.jshell;
    requires jbcrypt;


    // Without this, JavaFX cannot instantiate GuiApplication.
    exports it.uniroma2.partyflow.launchers to javafx.graphics;

    // Without this, JavaFX cannot inject widgets into fields annotated with @FXML.
    opens it.uniroma2.partyflow.graphic_controllers.CLI to javafx.fxml;
    opens it.uniroma2.partyflow.graphic_controllers.GUI to javafx.fxml;
}
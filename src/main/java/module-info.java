module it.uniroma2.partyflow {

    // Without this, I can't use the classes defined in these module.
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires com.opencsv;


    // Without this, JavaFX cannot instantiate GuiApplication.
    exports it.uniroma2.partyflow.launcher to javafx.graphics;

    // Without this, JavaFX cannot inject widgets into fields annotated with @FXML.
    opens it.uniroma2.partyflow.graphic_controller.signupgraphiccontroller to javafx.fxml;
    opens it.uniroma2.partyflow.graphic_controller.firstscenegraphiccontroller to javafx.fxml;
    opens it.uniroma2.partyflow.graphic_controller.logingraphiccontroller to javafx.fxml;
}
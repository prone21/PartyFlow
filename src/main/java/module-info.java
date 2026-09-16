module it.uniroma2.partyflow {

    // Without this, I can't use the classes defined in these module.
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    // Without this, JavaFX cannot instantiate GuiApplication.
    exports it.uniroma2.partyflow.launcher to javafx.graphics;

    // Without this, JavaFX cannot inject widgets into fields annotated with @FXML.
    opens it.uniroma2.partyflow.graphic_controller to javafx.fxml;
}
package it.uniroma2.partyflow.launcher;
import it.uniroma2.partyflow.utilities.NavigatorSingleton;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;


public class GuiApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        NavigatorSingleton nav = NavigatorSingleton.getInstance(stage);
        nav.gotoView("/it/uniroma2/partyflow/view/view_in_common/firstScene.fxml");

    }
}

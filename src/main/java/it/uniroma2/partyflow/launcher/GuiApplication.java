package it.uniroma2.partyflow.launcher;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


public class GuiApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader =
                new FXMLLoader(getClass().getResource("/it/uniroma2/partyflow/view/view_in_common/firstScene.fxml"));

        Scene scene = new Scene(loader.load());

        stage.setTitle("PartyFlow");
        stage.setScene(scene);

        stage.show();
    }
}

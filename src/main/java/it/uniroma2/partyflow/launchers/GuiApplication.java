package it.uniroma2.partyflow.launchers;
import it.uniroma2.partyflow.errorhandlers.HandlerSystemError;
import it.uniroma2.partyflow.utilities.NavigatorSingleton;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;


public class GuiApplication extends Application implements UIApplication {
    @Override
    public void start(Stage stage){
        try {
            NavigatorSingleton nav = NavigatorSingleton.getInstance(stage);
            nav.gotoView("/it/uniroma2/partyflow/view/view_in_common/firstScene.fxml");
        }
        catch (IOException e){
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(e);
        }

    }

    public void launch(){
        Application.launch(GuiApplication.class);
    }
}

package it.uniroma2.partyflow.graphic_controllers.GUI;
import it.uniroma2.partyflow.errorhandlers.HandlerSystemError;
import it.uniroma2.partyflow.exceptions.systemexception.InvalidPersistenceTypeException;
import it.uniroma2.partyflow.utilities.NavigatorSingleton;
import javafx.fxml.FXML;
import java.io.IOException;

public class FirstSceneGraphicControllerGUI {
    @FXML
    public void login(){
        try{
            NavigatorSingleton nav = NavigatorSingleton.getInstance();
            nav.gotoView("/it/uniroma2/partyflow/view/view_in_common/login.fxml");
        }
        catch (InvalidPersistenceTypeException | IOException e){
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(e);
        }
    }

    @FXML
    public void signUp (){
        try {
            NavigatorSingleton nav = NavigatorSingleton.getInstance();
            nav.gotoView("/it/uniroma2/partyflow/view/view_in_common/signUp.fxml");
        }
        catch (InvalidPersistenceTypeException | IOException e){
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(e);
        }
    }
}

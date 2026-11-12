package it.uniroma2.partyflow.graphic_controllers.GUI;
import it.uniroma2.partyflow.errorhandlers.HandlerSystemError;
import it.uniroma2.partyflow.exceptions.systemexception.InternalException;
import it.uniroma2.partyflow.utilities.navigators.GUINavigatorSingleton;
import javafx.fxml.FXML;

public class FirstSceneGraphicControllerGUI {
    @FXML
    public void login(){
        try{
            GUINavigatorSingleton nav = GUINavigatorSingleton.getInstance();
            nav.goToView("/it/uniroma2/partyflow/view/view_in_common/login.fxml");
        }
        catch (InternalException e){
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(e);
            handlerSystemError.handleFatalError(e);
        }
    }

    @FXML
    public void signUp (){
        try {
            GUINavigatorSingleton nav = GUINavigatorSingleton.getInstance();
            nav.goToView("/it/uniroma2/partyflow/view/view_in_common/signUp.fxml");
        }
        catch (InternalException e){
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(e);
            handlerSystemError.handleFatalError(e);
        }
    }
}

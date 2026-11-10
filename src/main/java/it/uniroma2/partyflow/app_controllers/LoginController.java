package it.uniroma2.partyflow.app_controllers;

import it.uniroma2.partyflow.beans.LoginCredentialsBean;
import it.uniroma2.partyflow.daos.dao_factories.DaoAbstractFactory;
import it.uniroma2.partyflow.daos.dao_interfaces.UserDaoInterface;
import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.errorhandlers.HandlerSystemError;
import it.uniroma2.partyflow.exceptions.daoexception.*;
import it.uniroma2.partyflow.exceptions.uiexception.ApplicationException;
import it.uniroma2.partyflow.models.LoginCredential;

public class LoginController {
    public AccountType login(LoginCredentialsBean cred) throws ApplicationException{
        try {
            // ----------- convert bean in model --------------
            LoginCredential usCred = new LoginCredential(cred.getEmail(), cred.getPassword());
            // -----------------------------------------------

            // pass the model to the persistence layer
            DaoAbstractFactory factory = DaoAbstractFactory.getFactory();
            UserDaoInterface loginDao = factory.getUserDao();
            return loginDao.login(usCred);

        } catch (PersistenceGenericException | PersistenceConstraintViolationException e) {
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError("Login failed: " + e.getMessage(), e);

            throw new ApplicationException("An unexpected error occurred while logging into your account. " +
                    "Please try again.");

        } catch (PersistenceServiceUnavailableException e) {
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError("Login failed: " + e.getMessage(), e);

            throw new ApplicationException("The service is temporarily unavailable. " +
                    "Please try again later.");

        } catch (EntityNotFound e) {
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError("Login failed: " + e.getMessage(), e);

            throw new ApplicationException("Invalid username or password. Please try again.");

        } catch (InvalidPersistenceTypeException e) {

            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError("Login failed: " + e.getMessage(), e);

            handlerSystemError.handleFatalError(e);

            return null;
        }
    }
}

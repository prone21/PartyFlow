package it.uniroma2.partyflow.app_controllers;

import it.uniroma2.partyflow.beans.LoginCredentialsBean;
import it.uniroma2.partyflow.daos.dao_factories.DaoAbstractFactory;
import it.uniroma2.partyflow.daos.dao_interfaces.UserDaoInterface;
import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.errorhandlers.HandlerSystemError;
import it.uniroma2.partyflow.exceptions.daoexception.*;
import it.uniroma2.partyflow.exceptions.uiexception.ApplicationException;
import it.uniroma2.partyflow.models.LoginCredential;
import it.uniroma2.partyflow.utilities.UserSession;


/**
 * Application controller responsible for handling the user login process.
 *
 * <p>Converts login credentials from a Bean into a domain model and
 * delegates authentication to the persistence layer through the DAO.
 * It also handles persistence-related exceptions by logging errors
 * and translating them into application-level exceptions.</p>
 */


public class LoginController {
    private static final String LOGIN_ERROR_STR = "Login failed: ";

    /**
     * Authenticates a user using the provided login credentials.
     *
     * <p>Converts the LoginCredentialsBean into a domain model and
     * delegates authentication to the appropriate DAO, independently
     * of the configured persistence technology.</p>
     *
     * <p>Handles persistence-related exceptions by logging errors
     * and translating recoverable failures into application-level
     * exceptions. Fatal errors are delegated to HandlerSystemError,
     * which coordinates application termination.</p>
     *
     * @param cred the bean containing the user's login credentials
     * @return the account type of the authenticated user
     * @throws ApplicationException if the credentials are invalid,
     * the persistence service is unavailable, or an unexpected
     * persistence error occurs
     */
    public AccountType login(LoginCredentialsBean cred) throws ApplicationException{
        try {
            // ----------- convert bean in model --------------
            LoginCredential usCred = new LoginCredential(cred.getEmail(), cred.getPassword());
            // -----------------------------------------------

            // pass the model to the persistence layer
            DaoAbstractFactory factory = DaoAbstractFactory.getFactory();
            UserDaoInterface loginDao = factory.getUserDao();
            // -----------------------------------------------

            // instance the user session
            AccountType at = loginDao.login(usCred);
            UserSession.initializeSession(usCred.getEmail(), at);

            return at;

        } catch (PersistenceGenericException | PersistenceConstraintViolationException e) {
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(LOGIN_ERROR_STR + e.getMessage(), e);

            throw new ApplicationException("An unexpected error occurred while logging into your account. " +
                    "Please try again.");

        } catch (PersistenceServiceUnavailableException e) {
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(LOGIN_ERROR_STR + e.getMessage(), e);

            throw new ApplicationException("The service is temporarily unavailable. " +
                    "Please try again later.");

        } catch (EntityNotFound e) {
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(LOGIN_ERROR_STR + e.getMessage(), e);

            throw new ApplicationException("Invalid username or password. Please try again.");

        } catch (InvalidPersistenceTypeException e) {

            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(LOGIN_ERROR_STR + e.getMessage(), e);

            handlerSystemError.handleFatalError(e);
            return null;
        }
    }
}

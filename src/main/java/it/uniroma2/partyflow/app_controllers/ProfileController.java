package it.uniroma2.partyflow.app_controllers;

import it.uniroma2.partyflow.beans.UserBean;
import it.uniroma2.partyflow.daos.dao_factories.DaoAbstractFactory;
import it.uniroma2.partyflow.daos.dao_interfaces.UserDaoInterface;
import it.uniroma2.partyflow.errorhandlers.HandlerSystemError;
import it.uniroma2.partyflow.exceptions.daoexception.EntityNotFound;
import it.uniroma2.partyflow.exceptions.daoexception.InvalidPersistenceTypeException;
import it.uniroma2.partyflow.exceptions.daoexception.PersistenceGenericException;
import it.uniroma2.partyflow.exceptions.daoexception.PersistenceServiceUnavailableException;
import it.uniroma2.partyflow.exceptions.systemexception.InternalException;
import it.uniroma2.partyflow.exceptions.uiexception.ApplicationException;
import it.uniroma2.partyflow.exceptions.uiexception.InvalidFieldFormatException;
import it.uniroma2.partyflow.exceptions.uiexception.RequiredFieldException;
import it.uniroma2.partyflow.models.User;
import it.uniroma2.partyflow.utilities.UserSession;

import java.time.format.DateTimeFormatter;

/**
 * Application controller responsible for managing user profile operations.
 *
 * <p>Coordinates interactions with the persistence
 * layer through DAOs, manages the active user session, and translates
 * persistence exceptions into application-level exceptions.</p>
 */

public class ProfileController {
    private static final String PROFILE_ERRSTR = "Failed to retrieve user profile: ";

    /**
     * Logs out the currently authenticated user.
     *
     * <p>Invalidates the active user session and logs the successful
     * completion of the logout operation.</p>
     */
    public void logOut(){
        UserSession.logOut();
        HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
        handlerSystemError.logInfo("User logged out successfully. Session invalidated.");
    }

    /**
     * Retrieves the profile information of the currently authenticated user.
     *
     * <p>Obtains the user's email address from the active session and
     * retrieves the corresponding User entity through the persistence layer.
     * Converts the retrieved entity into a UserBean for presentation purposes.</p>
     *
     * <p>Handles persistence-related exceptions by logging the errors
     * and translating recoverable failures into application-level exceptions.
     * Unrecoverable errors are delegated to the system error handler.</p>
     *
     * @return a UserBean containing the authenticated user's profile information,
     *         or null if a fatal error is handled
     * @throws ApplicationException if the user cannot be found or a persistence
     *         error prevents the profile from being retrieved
     * @throws RequiredFieldException if a required field in the UserBean is missing
     * @throws InvalidFieldFormatException if a field in the UserBean has an invalid format
     */
    public UserBean retriveProfile() throws ApplicationException, RequiredFieldException, InvalidFieldFormatException {
        try{
            // 1) retrive session
            UserSession session = UserSession.getInstance();
            String email = session.getUserEmail();
            // -----------------------------------

            // 2) obtain dao and request the user
            DaoAbstractFactory daoAbstractFactory = DaoAbstractFactory.getFactory();
            UserDaoInterface userDao = daoAbstractFactory.getUserDao();
            User user = userDao.retriveUserByEmail(email);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String date = user.getDateOfBirth().format(formatter);

            return new UserBean(user.getName(), user.getSurname(), date,
                    user.getEmail(), user.getPwd(), user.getGender(), user.getAccountType());
            // -----------------------------------
        }
        catch (PersistenceGenericException e){
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(PROFILE_ERRSTR + e.getMessage(),e);

            throw new ApplicationException("An unexpected error occurred while loading your profile." +
                    " Please try again.");
        }
        catch (PersistenceServiceUnavailableException e){
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(PROFILE_ERRSTR + e.getMessage(),e);

            throw new ApplicationException("We couldn't load your profile because the service is currently unavailable. " +
                    "Please try again later.");
        }

        catch (InvalidPersistenceTypeException | InternalException e){

            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(PROFILE_ERRSTR + e.getMessage(),e);

            handlerSystemError.handleFatalError(e);
            return null;
        }

        catch (EntityNotFound e) {
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(PROFILE_ERRSTR + e.getMessage(), e);

            throw new ApplicationException("We couldn't load your profile because your account could not be found. Please try logging in again.");

        }

    }

}

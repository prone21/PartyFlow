package it.uniroma2.partyflow.app_controllers;

import it.uniroma2.partyflow.beans.LoginCredentialsBean;
import it.uniroma2.partyflow.daos.dao_factories.DaoAbstractFactory;
import it.uniroma2.partyflow.daos.dao_interfaces.UserDaoInterface;
import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.errorhandlers.HandlerSystemError;
import it.uniroma2.partyflow.exceptions.daoexception.EntityNotFound;
import it.uniroma2.partyflow.exceptions.daoexception.PersistenceConstraintViolationException;
import it.uniroma2.partyflow.exceptions.daoexception.PersistenceGenericException;
import it.uniroma2.partyflow.exceptions.daoexception.PersistenceServiceUnavailableException;
import it.uniroma2.partyflow.exceptions.uiexception.ApplicationException;
import it.uniroma2.partyflow.model.LoginCredential;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class LoginappController {
    public AccountType login(LoginCredentialsBean cred) throws ApplicationException {

        try{
            // ----------- convert bean in model --------------
            LoginCredential usCred = new LoginCredential(cred.getEmail(),cred.getPassword());
            // -----------------------------------------------

            DaoAbstractFactory factory = DaoAbstractFactory.getFactory();
            UserDaoInterface loginDao = factory.getUserDao();
            return loginDao.login(usCred);

        }

        catch (PersistenceServiceUnavailableException e){
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError("Login failed: " + e.getMessage(),e);

            throw new ApplicationException( "The service is temporarily unavailable. " +
                    "Please try again later.");
        }

        catch (EntityNotFound e){
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError("Login failed: " + e.getMessage(),e);

            throw new ApplicationException("The user doesn't exist");
        }

        catch (PersistenceGenericException | PersistenceConstraintViolationException e){
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError("Login failed: " + e.getMessage(),e);

            throw new ApplicationException("An unexpected error occurred while creating your account. " +
                    "Please try again.");
        }
    }
}

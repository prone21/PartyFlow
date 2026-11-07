package it.uniroma2.partyflow.app_controllers;

import it.uniroma2.partyflow.beans.UserBean;
import it.uniroma2.partyflow.daos.dao_interfaces.UserDaoInterface;
import it.uniroma2.partyflow.daos.dao_factories.DaoAbstractFactory;
import it.uniroma2.partyflow.errorhandlers.HandlerSystemError;
import it.uniroma2.partyflow.exceptions.daoexception.*;
import it.uniroma2.partyflow.exceptions.uiexception.ApplicationException;
import it.uniroma2.partyflow.models.User;
import it.uniroma2.partyflow.utilities.PasswordProtector;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class SignUpController {

    public void createAccount(UserBean signUpBean)  {

        try{
            // --------convert the bean to the model---------

            // ------------- converts the date --------------
            // defines the pattern
            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("dd/MM/uuuu");

            LocalDate date =
                    LocalDate.parse(signUpBean.getDateOfBirth(), formatter);
            // -----------------------------------------------

            // ---------- makes the password secure ----------
            String hashedPassword = PasswordProtector.hashPassword(signUpBean.getPassword());
            // -----------------------------------------------

            // ----------- convert bean in model --------------
            User user = new User(signUpBean.getName(), signUpBean.getSurname(),
                    signUpBean.getEmail(), hashedPassword,
                    date, signUpBean.getAccountType(),
                    signUpBean.getGender());

            // ------------------------------------------------


            // pass the model to the persistence layer
            DaoAbstractFactory factory = DaoAbstractFactory.getFactory();
            UserDaoInterface signUpDao = factory.getUserDao();
            signUpDao.signUp(user);
        }

        catch (PersistenceGenericException e){
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError("SignUp failed: " + e.getMessage(),e);

            throw new ApplicationException("An unexpected error occurred while creating your account. " +
                    "Please try again.");
        }

        catch (PersistenceServiceUnavailableException e){
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError("SignUp failed: " + e.getMessage(),e);

            throw new ApplicationException("We couldn't complete your registration because the service is currently unavailable. " +
                    "Please try again later.");
        }

        catch (DuplicateEntityException | PersistenceConstraintViolationException e){
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError("SignUp failed: " + e.getMessage(),e);

            throw new ApplicationException("This account already exists.");
        }

        catch (InvalidPersistenceTypeException e){

            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError("SignUp failed: " + e.getMessage(),e);

            handlerSystemError.handleFatalError(e);
        }

    }

}

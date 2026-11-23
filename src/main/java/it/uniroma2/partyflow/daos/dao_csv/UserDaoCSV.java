package it.uniroma2.partyflow.daos.dao_csv;

import com.opencsv.CSVReaderHeaderAware;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import it.uniroma2.partyflow.daos.dao_interfaces.UserDaoInterface;
import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.errorhandlers.HandlerSystemError;
import it.uniroma2.partyflow.exceptions.daoexception.*;
import it.uniroma2.partyflow.models.LoginCredential;
import it.uniroma2.partyflow.models.User;
import it.uniroma2.partyflow.utilities.PasswordProtector;
import it.uniroma2.partyflow.utilities.configurators.PersistenceConfigurator;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;


/**
 * CSV implementation of the Data Access Object (DAO)
 * responsible for managing user persistence.
 *
 * <p>Provides operations for user registration and authentication
 * using a CSV file as a persistent data store.</p>
 *
 * <p>Uses the OpenCSV library to read and write user records
 * and PersistenceConfigurator to retrieve the CSV database directory.</p>
 */


public class UserDaoCSV implements UserDaoInterface {

    private String fileName = null;

    // --------------------------------loggers----------------------------------------------

    /**
     * Logs an exception through HandlerSystemError.
     *
     * @param e the exception to log
     */

    private static void stdLog(Exception e) {
        // log the error
        HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
        handlerSystemError.logError(e);
    }


    /**
     * Logs an IOException through HandlerSystemError.
     *
     * @param e the IOException to log
     */

    private static void logIOException(IOException e){
        HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
        handlerSystemError.logError(e);
    }


    /**
     * Logs a CSV validation exception through HandlerSystemError.
     *
     * @param e the CsvValidationException to log
     */

    private static void logCsvValidationException(CsvValidationException e){
        // CsvValidationException is thrown when a CSV record fails
        // validation, for example when a configured row or line validator detects invalid data during file reading.

        HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
        handlerSystemError.logError(e);
    }

    // ------------------------------------------------------------------------------------


    /**
     * Initializes the file path associated with this DAO.
     *
     * <p>Retrieves the CSV database directory from PersistenceConfigurator
     * and sets the path to the Users.csv file.</p>
     *
     * @throws InvalidPersistenceTypeException if CSV is not the
     * configured persistence type
     * @throws PersistenceServiceUnavailableException if an IOException
     * occurs while loading the persistence configuration
     */

    private void initialize() throws InvalidPersistenceTypeException, PersistenceServiceUnavailableException{
        try {
            // obtain the db directory
            PersistenceConfigurator pc = PersistenceConfigurator.getInstance();
            String dbCSV = pc.getDirectoryCSVDB();
            // set the file which the dao is associated
            fileName = dbCSV + "//Users.csv";
        }

        catch (IOException e){
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError("Failed to access CSV file.",e);
            throw new PersistenceServiceUnavailableException();
        }

    }



    /**
     * Searches for a stored user by email address.
     *
     * <p>Reads the CSV file sequentially using CSVReaderHeaderAware,
     * which represents each record as a map associating column
     * headers with their corresponding values.</p>
     *
     * <p>Uses try-with-resources to automatically close the reader.</p>
     *
     * @param userEmail the email address identifying the user
     * @return the map containing the user's data, or an empty map
     * if no matching user is found
     * @throws IOException if an I/O error occurs
     * @throws CsvValidationException if a CSV record fails validation
     */

    private Map<String, String> findUserByEmail(String userEmail) throws IOException, CsvValidationException {
        try (CSVReaderHeaderAware reader = new CSVReaderHeaderAware(new FileReader(fileName))){

            Map<String, String> row;

            while ((row = reader.readMap()) != null) {

                if (userEmail.equals(row.get("email"))) {
                    return row;
                }
            }

            return Map.of(); // returns an empty map
        }
    }


    /**
     * Checks whether a user with the specified email address
     * already exists in the CSV database.
     *
     * <p>Uses try-with-resources to automatically close the reader.</p>
     *
     * @param userEmail the email address to check
     * @return true if the user exists, false otherwise
     * @throws IOException if an I/O error occurs
     * @throws CsvValidationException if a CSV record fails validation
     */

    private boolean exists(String userEmail)
            throws IOException, CsvValidationException {
        return !this.findUserByEmail(userEmail).isEmpty();
    }


    /**
     * Persists a new user in the CSV database.
     *
     * <p>Initializes the CSV file path and converts the user's
     * data into an array of strings.</p>
     *
     * <p>Checks whether a user with the same email address already
     * exists before appending the new record to the CSV file.</p>
     *
     * <p>Uses try-with-resources to automatically close the writer.
     * Logs and translates I/O and CSV validation exceptions
     * into application-specific persistence exceptions.</p>
     *
     * @param user the user to persist
     * @throws PersistenceServiceUnavailableException if an I/O error occurs
     * @throws DuplicateEntityException if the user already exists
     * @throws InvalidPersistenceTypeException if CSV is not the
     * configured persistence type
     * @throws PersistenceConstraintViolationException if a CSV record
     * fails validation
     */

    @Override
    public void signUp(User user) throws PersistenceServiceUnavailableException, DuplicateEntityException,
            InvalidPersistenceTypeException, PersistenceConstraintViolationException {

        try{
            this.initialize();
            // convert user to list of string
            String [] newUser = {
                    user.getEmail(),
                    user.getName(),
                    user.getSurname(),
                    user.getPwd(),
                    user.getDateOfBirth().toString(),
                    user.getGender().name(),
                    user.getAccountType().name()
            };

            // check if the user already exists
            if (this.exists(user.getEmail())){
                throw new DuplicateEntityException();
            }

            // true activate append mode
            try (CSVWriter writer = new CSVWriter(new FileWriter(fileName, true)) ){
                writer.writeNext(newUser);
            }

        }

        catch (PersistenceServiceUnavailableException | DuplicateEntityException |
                InvalidPersistenceTypeException e) {

            UserDaoCSV.stdLog(e);
            throw e;

        }
        catch (IOException e){
            UserDaoCSV.logIOException(e);
            throw new PersistenceServiceUnavailableException();
        }
        catch (CsvValidationException e){
            UserDaoCSV.logCsvValidationException(e);
            throw new PersistenceConstraintViolationException();
        }

    }



    /**
     * Authenticates a user against the CSV database.
     *
     * <p>Retrieves the user's record by email address and verifies
     * the provided password against the stored password hash
     * through PasswordProtector.</p>
     *
     * <p>If authentication succeeds, returns the corresponding account type.
     * Logs and translates I/O and CSV validation exceptions
     * into application-specific persistence exceptions.</p>
     *
     * @param cred the login credentials provided by the user
     * @return the account type of the authenticated user
     * @throws EntityNotFound if the user does not exist or the password is incorrect
     * @throws PersistenceServiceUnavailableException if an I/O error occurs
     * @throws InvalidPersistenceTypeException if CSV is not the
     * configured persistence type
     * @throws PersistenceConstraintViolationException if a CSV record
     * fails validation
     */

    @Override
    public AccountType login(LoginCredential cred) throws EntityNotFound, PersistenceServiceUnavailableException,
            InvalidPersistenceTypeException, PersistenceConstraintViolationException {

        try{
            this.initialize();

            Map<String, String> row = this.findUserByEmail(cred.getEmail());

            if ( (row.isEmpty()) || !(PasswordProtector.checkPassword(cred.getPassword(), row.get("password"))) ){
                throw new EntityNotFound();
            }
            else{
                return AccountType.valueOf(row.get("account_type"));
            }
        }

        catch (PersistenceServiceUnavailableException |
               InvalidPersistenceTypeException | EntityNotFound e) {

            UserDaoCSV.stdLog(e);
            throw e;
        }

        catch (IOException e){
            UserDaoCSV.logIOException(e);
            throw new PersistenceServiceUnavailableException();
        }
        catch (CsvValidationException e){
            UserDaoCSV.logCsvValidationException(e);
            throw new PersistenceConstraintViolationException();
        }

    }

    @Override
    public User retriveUserByEmail(String email){
        return null;
    }

}
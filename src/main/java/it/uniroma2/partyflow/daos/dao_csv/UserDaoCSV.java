package it.uniroma2.partyflow.daos.dao_csv;

import com.opencsv.CSVReaderHeaderAware;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import it.uniroma2.partyflow.daos.dao_interfaces.UserDaoInterface;
import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.errorhandlers.HandlerSystemError;
import it.uniroma2.partyflow.exceptions.daoexception.*;
import it.uniroma2.partyflow.exceptions.daoexception.InvalidPersistenceTypeException;
import it.uniroma2.partyflow.models.LoginCredential;
import it.uniroma2.partyflow.models.User;
import it.uniroma2.partyflow.utilities.PasswordProtector;
import it.uniroma2.partyflow.utilities.configurators.PersistenceConfigurator;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class UserDaoCSV implements UserDaoInterface {

    private static String file_name = null;


    private void initialize() throws PersistenceServiceUnavailableException, InvalidPersistenceTypeException {
        try {
            // obtain the db directory
            PersistenceConfigurator pc = PersistenceConfigurator.getInstance();
            String dbCSV = pc.getDirectoryCSVDB();
            // set the file which the dao is associated
            file_name = dbCSV + "//Users.csv";
        }

        catch (IOException e){
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError("Failed to access CSV file.",e);
            throw new PersistenceServiceUnavailableException();
        }

    }


    private Map<String, String> findUserByEmail(String userEmail) throws IOException, CsvValidationException {
        CSVReaderHeaderAware reader =
                new CSVReaderHeaderAware(new FileReader(file_name));

        Map<String, String> row;

        while ((row = reader.readMap()) != null) {

            if(userEmail.equals(row.get("email"))) {
                return row;
            }
        }

        return null;
    }

    private boolean exists (String userEmail) throws IOException, CsvValidationException {
        CSVReaderHeaderAware reader =
                new CSVReaderHeaderAware(new FileReader(file_name));

        Map<String, String> row;

        while ((row = reader.readMap()) != null) {

            if(userEmail.equals(row.get("email"))) {
                return true;
            }
        }

        return false;
    }


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
            CSVWriter writer = new CSVWriter(new FileWriter(file_name, true));
            writer.writeNext(newUser);

        }

        catch (PersistenceServiceUnavailableException | DuplicateEntityException |
                InvalidPersistenceTypeException e) {

            // log the error
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(e);
            // throw the exception
            throw e;
        }
        catch (IOException e){
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(e);
            throw new PersistenceServiceUnavailableException();
        }
        catch (CsvValidationException e){

            /*  CsvValidationException is thrown when a CSV record fails
                validation, for example when a configured row or line
                validator detects invalid data during file reading.
            */

            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(e);
            throw new PersistenceConstraintViolationException();
        }

    }


    @Override
    public AccountType login(LoginCredential cred) throws EntityNotFound, PersistenceServiceUnavailableException,
            InvalidPersistenceTypeException, PersistenceConstraintViolationException {

        try{
            this.initialize();

            Map<String, String> row = this.findUserByEmail(cred.getEmail());

            if ( (row == null) || !(PasswordProtector.checkPassword(cred.getPassword(), row.get("password"))) ){
                throw new EntityNotFound();
            }
            else{
                return AccountType.valueOf(row.get("account_type"));
            }
        }

        catch (PersistenceServiceUnavailableException |
               InvalidPersistenceTypeException | EntityNotFound e) {

            // log the error
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(e);
            // throw the exception
            throw e;
        }

        catch (IOException e){
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(e);
            throw new PersistenceServiceUnavailableException();
        }
        catch (CsvValidationException e){

            /*  CsvValidationException is thrown when a CSV record fails
                validation, for example when a configured row or line
                validator detects invalid data during file reading.
            */

            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(e);
            throw new PersistenceConstraintViolationException();
        }

    }

}
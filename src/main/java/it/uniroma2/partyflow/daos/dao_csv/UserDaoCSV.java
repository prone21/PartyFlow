package it.uniroma2.partyflow.daos.dao_csv;

import com.opencsv.CSVReaderHeaderAware;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import it.uniroma2.partyflow.daos.dao_interfaces.UserDaoInterface;
import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.errorhandlers.HandlerSystemError;
import it.uniroma2.partyflow.exceptions.daoexception.*;
import it.uniroma2.partyflow.model.LoginCredential;
import it.uniroma2.partyflow.model.User;
import it.uniroma2.partyflow.utilities.configurators.PersistenceConfigurator;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class UserDaoCSV implements UserDaoInterface {

    private String file_name;


    private void initialize() throws InvalidPersistenceTypeException {
        // obtain the db directory
        PersistenceConfigurator pc = PersistenceConfigurator.getInstance();
        String dbCSV = pc.getDirectoryCSVDB();
        // set the file which the dao is associated
        this.file_name = dbCSV + "//Users.csv";
    }


    private Map<String, String> exists(String email) throws IOException, CsvValidationException {
        CSVReaderHeaderAware reader =
                new CSVReaderHeaderAware(new FileReader(this.file_name));

        Map<String, String> row;

        while ((row = reader.readMap()) != null) {

            if(email.equals(row.get("email"))) {
                return row;
            }
        }

        return null;
    }


    @Override
    public void signUp(User user) throws PersistenceServiceUnavailableException, DuplicateEntityException,
            PersistenceGenericException, PersistenceConstraintViolationException {

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
            if (this.exists(user.getEmail()) == null){
                DuplicateEntityException e = new DuplicateEntityException();
                HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
                handlerSystemError.logError(e);
                throw e;
            }


            // true activate append mode
            CSVWriter writer = new CSVWriter(new FileWriter(this.file_name, true));
            writer.writeNext(newUser);
        }

        catch (InvalidPersistenceTypeException e) {
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(e);
            throw new PersistenceServiceUnavailableException();
        }

        catch (CsvValidationException e) {
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(e);
            throw new PersistenceConstraintViolationException();
        }

        catch (IOException e) {
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(e);
            throw new PersistenceGenericException();
        }

    }


    @Override
    public AccountType login(LoginCredential cred) throws PersistenceServiceUnavailableException,
            PersistenceConstraintViolationException, PersistenceGenericException, EntityNotFound{

        try{
            this.initialize();

            Map<String, String> row = this.exists(cred.getEmail());

            if (row == null){
                EntityNotFound e = new EntityNotFound();
                HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
                handlerSystemError.logError(e);
                throw e;
            }
            else{
                return AccountType.valueOf(row.get("account_type"));
            }
        }

        catch (InvalidPersistenceTypeException e) {
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(e);
            throw new PersistenceServiceUnavailableException();
        }

        catch (CsvValidationException e) {
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(e);
            throw new PersistenceConstraintViolationException();
        }

        catch (IOException e) {
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(e);
            throw new PersistenceGenericException();
        }




    }

}
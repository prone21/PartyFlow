package it.uniroma2.partyflow.dao.logindao;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.model.LoginCredential;
import it.uniroma2.partyflow.utilities.Configurator;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class LoginDaoCSV implements LoginDaoInterface{

    private final LoginCredential cred;

    public LoginDaoCSV(LoginCredential cred) {
        this.cred = cred;
    }

    private AccountType checkCredentials()
            throws IOException, CsvValidationException {

        Configurator configurator = Configurator.getConfigurator();

        Path directory =
                Path.of(configurator.getdirectoryCSVDB());

        Path participantFile =
                directory.resolve("Participant.csv");

        Path partyPlannerFile =
                directory.resolve("PartyPlanner.csv");

        if (credentialsExist(participantFile)) {
            return AccountType.Participant;
        }

        if (credentialsExist(partyPlannerFile)) {
            return AccountType.PartyPlanner;
        }

        return AccountType.AccountNotExsist;
    }

    private boolean credentialsExist(Path filePath)
            throws IOException, CsvValidationException {

        try (CSVReader reader = new CSVReader(
                Files.newBufferedReader(
                        filePath,
                        StandardCharsets.UTF_8))) {

            String[] row;

            // Salta l'header
            reader.readNext();

            while ((row = reader.readNext()) != null) {

                String email = row[0];
                String password = row[3];

                if (email.equals(cred.getEmail()) &&
                        password.equals(cred.getPassword())) {

                    return true;
                }
            }
        }

        return false;
    }

    public AccountType login() {
        try{
            return checkCredentials();
        }
        catch (IOException e){
            e.printStackTrace();
            return null;
        }
        catch (CsvValidationException e1){
            e1.printStackTrace();
            return null;
        }

    }
}
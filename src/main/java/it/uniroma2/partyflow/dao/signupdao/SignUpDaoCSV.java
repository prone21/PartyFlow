package it.uniroma2.partyflow.dao.signupdao;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.model.User;
import it.uniroma2.partyflow.utilities.Configurator;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class SignUpDaoCSV implements SignUpDaoInterface {

    private final User cred;

    public SignUpDaoCSV(User cred) {
        this.cred = cred;
    }

    @Override
    public void createAccount() {

        try {
            Configurator configurator = Configurator.getConfigurator();

            Path directory = Path.of(configurator.getdirectoryCSVDB());

            String fileName;

            if (cred.getAccountType() == AccountType.Participant) {
                fileName = "Participant.csv";
            } else if (cred.getAccountType() == AccountType.PartyPlanner) {
                fileName = "PartyPlanner.csv";
            } else {
                throw new IllegalArgumentException("Unknown account type");
            }

            Path filePath = directory.resolve(fileName);

            if (emailAlreadyExists(filePath)) {
                throw new IllegalStateException(
                        "An account with email " + cred.getEmail() + " already exists"
                );
            }

            try (CSVWriter writer = new CSVWriter(
                    Files.newBufferedWriter(
                            filePath,
                            StandardCharsets.UTF_8,
                            java.nio.file.StandardOpenOption.APPEND
                    ))) {

                String[] userData = {
                        cred.getEmail(),
                        cred.getName(),
                        cred.getSurname(),
                        cred.getPwd(),
                        cred.getDateOfBirth().toString(),
                        cred.getGender() != null
                                ? cred.getGender().name()
                                : ""
                };

                writer.writeNext(userData);
            }

        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }

    private boolean emailAlreadyExists(Path filePath)
            throws IOException, CsvValidationException {

        try (CSVReader reader = new CSVReader(
                Files.newBufferedReader(filePath, StandardCharsets.UTF_8))) {

            String[] row;

            // salta l'header
            reader.readNext();

            while ((row = reader.readNext()) != null) {

                // email è la prima colonna
                if (row[0].equals(cred.getEmail())) {
                    return true;
                }
            }
        }

        return false;
    }
}
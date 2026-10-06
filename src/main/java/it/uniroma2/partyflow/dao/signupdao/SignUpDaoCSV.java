package it.uniroma2.partyflow.dao.signupdao;

import it.uniroma2.partyflow.model.User;

import java.nio.file.*;

public class SignUpDaoCSV implements SignUpDaoInterface {

    private User user;

    private static final Path FILE_PATH =
            Path.of("data", "users.csv");

    public SignUpDaoCSV() {}
    public void setCredentials(User user){
        this.user = user;
    }

    public SignUpDaoCSV(User user) {
        this.user = user;
    }

    public void createAccount() {
        System.out.println("TE EXTRANAMOS MESSI :-(");
    }
}
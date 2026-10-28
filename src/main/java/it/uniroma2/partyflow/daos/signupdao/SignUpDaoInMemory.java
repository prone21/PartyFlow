package it.uniroma2.partyflow.daos.signupdao;

import it.uniroma2.partyflow.model.User;

public class SignUpDaoInMemory implements SignUpDaoInterface {

    private final User cred;

    public SignUpDaoInMemory(User cred) {
        this.cred = cred;
    }

    @Override
    public void createAccount() {

        System.out.println(
                "Dummy registration completed for: " + cred.getEmail()
        );
    }
}
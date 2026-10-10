package it.uniroma2.partyflow.dao.signupdao;

import it.uniroma2.partyflow.model.User;

public class SignUpDaoNoPersistence implements SignUpDaoInterface {

    private final User cred;

    public SignUpDaoNoPersistence(User cred) {
        this.cred = cred;
    }

    @Override
    public void createAccount() {

        System.out.println(
                "Dummy registration completed for: " + cred.getEmail()
        );
    }
}
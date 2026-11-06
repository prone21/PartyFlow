package it.uniroma2.partyflow.model;

import it.uniroma2.partyflow.errorhandlers.HandlerSystemError;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginCredential {

    private String email;
    private String password;

    public LoginCredential(String email, String password) {
        this.setEmail(email);
        this.setPassword(password);
    }


    private String hashPassword (String password) throws NoSuchAlgorithmException {

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes =
                digest.digest(password.getBytes(StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder();

        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword (String pwd)  {
        try {
            this.password = this.hashPassword(pwd);
        }
        catch (NoSuchAlgorithmException e){
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(e);
        }
    }
}

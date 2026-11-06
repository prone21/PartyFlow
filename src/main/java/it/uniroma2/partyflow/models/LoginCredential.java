package it.uniroma2.partyflow.models;

public class LoginCredential {

    private String email;
    private String password;

    public LoginCredential(String email, String password) {
        this.setEmail(email);
        this.setPassword(password);
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
        this.password = pwd;
    }
}

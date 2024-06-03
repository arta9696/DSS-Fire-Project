package app.DataObjectModels;

import app.Database.DBCon;

import java.util.Optional;

public class User {
    public String login;
    public String pass_hash;
    public String role;
    public byte[] salt;

    public User(String login, String pass_hash, String role, byte[] salt) {
        this.login = login;
        this.pass_hash = pass_hash;
        this.role = role;
        this.salt = salt;
    }
}
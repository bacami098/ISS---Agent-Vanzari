package org.example.agentvanzari.domain;

import java.io.Serializable;

public class User extends Entity implements Serializable {
    private String username;
    private String parola;

    public User(int id, String username, String parola) {
        super(id);
        this.username = username;
        this.parola = parola;
    }

    @Override
    public int getId()
    {
        return super.getId();
    }

    public String getUsername() {
        return username;
    }

    public String getParola() {
        return parola;
    }
}

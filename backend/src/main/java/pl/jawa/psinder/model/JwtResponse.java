package pl.jawa.psinder.model;

import java.io.Serializable;

public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwttoken;
    private final String username;
    private final long id;

    public JwtResponse(String jwttoken, String username, long id) {
        this.jwttoken = jwttoken;
        this.username = username;
        this.id = id;
    }

    public String getToken() {
        return this.jwttoken;
    }

    public String getUsername() {
        return username;
    }

    public long getId() {
        return id;
    }
}


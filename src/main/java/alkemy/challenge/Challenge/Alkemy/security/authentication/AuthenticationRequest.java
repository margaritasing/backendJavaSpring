package alkemy.challenge.Challenge.Alkemy.security.authentication;

import alkemy.challenge.Challenge.Alkemy.model.User;
import lombok.Data;


import java.io.Serializable;

@Data
public class AuthenticationRequest implements Serializable {

    private User user;

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AuthenticationRequest() {
    }

    public AuthenticationRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }
}

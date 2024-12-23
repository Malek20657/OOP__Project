import java.util.*;
abstract class User {
    String username;
    String password;
    Date dateOfBirth;

    User(String username, String password, Date dateOfBirth) {
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
    }

    public String getUsername() {
        return username;
    }
}
package co.justbeta.models;

import com.orm.SugarRecord;

/**
 * Created by barnabas on 10/11/15
 */
public class User extends SugarRecord<User> {
    String userId;
    String userName;
    String title;
    String company;
    String auth;

    public User() {
    }

    public User(String userId, String userName, String title, String company, String auth) {
        this.userId = userId;
        this.userName = userName;
        this.title = (title == null) ? "" : title;
        this.company = company;
        this.auth = auth;
    }

    public String getEmail() {
        return userName;
    }

    public String getUserId() {
        return userId;
    }

    public String getAuth() {
        return auth;
    }
}

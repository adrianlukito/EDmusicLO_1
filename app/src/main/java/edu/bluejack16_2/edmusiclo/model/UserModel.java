package edu.bluejack16_2.edmusiclo.model;

/**
 * Created by Asus on 6/20/2017.
 */

public class UserModel {

    String fullname;
    String email;
    String password;
    String id;



    public UserModel() {

    }

    public UserModel(String id, String fullname, String email, String password) {
        this.fullname = fullname;
        this.email = email;
        this.password = password;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getFullname() {
        return fullname;
    }

    public String getPassword() {
        return password;
    }
}

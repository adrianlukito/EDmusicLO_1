package edu.bluejack16_2.edmusiclo.model;

/**
 * Created by Asus on 7/29/2017.
 */

public class Comment {
    public String userEmail;
    public String text;

    public String getText() {
        return text;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public Comment(String userEmail, String text) {
        this.userEmail = userEmail;
        this.text = text;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Comment() {
    }

    public void setText(String text) {
        this.text = text;
    }
}

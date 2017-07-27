package edu.bluejack16_2.edmusiclo.model;

/**
 * Created by Asus on 7/19/2017.
 */

public class FavoriteSong {

    String email;
    int songID;


    public int getSongID() {
        return songID;
    }

    public String getEmailUser() {
        return email;
    }

    public void setEmailUser(String emailUser) {
        this.email = emailUser;
    }

    public void setSongID(int songID) {
        this.songID = songID;
    }

    public FavoriteSong(String emailUser, int songID) {
        this.email = emailUser;
        this.songID = songID;
    }
}

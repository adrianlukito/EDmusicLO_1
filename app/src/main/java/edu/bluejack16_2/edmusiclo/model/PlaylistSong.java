package edu.bluejack16_2.edmusiclo.model;

import java.util.Vector;

/**
 * Created by Asus on 7/28/2017.
 */

public class PlaylistSong {

    public String email;
    public String name;
    public Vector<Integer> idSongs;

    public  PlaylistSong (){
        idSongs = new Vector<Integer>();
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIdSongs(Vector<Integer> idSongs) {
        this.idSongs = idSongs;
    }

    public void addIdSong(Integer idSong){
        idSongs.add(idSong);
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vector<Integer> getIdSongs() {
        return idSongs;
    }
}

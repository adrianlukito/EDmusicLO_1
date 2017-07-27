package edu.bluejack16_2.edmusiclo.model;

import java.util.Vector;

/**
 * Created by Asus on 7/20/2017.
 */

public class Timeline {

    String id;

    public String user;
    public String songName;

    public Vector<String> likes;
    public Vector<String> dislikes;

    public Vector<String> comments;

    public Timeline(){
        likes = new Vector<String>();
        dislikes = new Vector<String>();
        comments = new Vector<String>();
    }

    public void addLike(String userID){
        likes.add(userID);
    }

    public void addDislike(String userID){
        dislikes.add(userID);
    }

    public void comment(String comment){
        comments.add(comment);
    }

    public String getComment(int index){
        return comments.get(index);
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public void setComments(Vector<String> comments) {
        this.comments = comments;
    }

    public void setDislikes(Vector<String> dislikes) {
        this.dislikes = dislikes;
    }

    public void setLikes(Vector<String> likes) {
        this.likes = likes;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}

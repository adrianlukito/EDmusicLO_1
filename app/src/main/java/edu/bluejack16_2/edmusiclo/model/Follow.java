package edu.bluejack16_2.edmusiclo.model;

/**
 * Created by Asus on 7/10/2017.
 */

public class Follow {

    public String followed;
    public String following;

    public Follow(){}

    public Follow(String followed, String following) {
        this.followed = followed;
        this.following = following;
    }

    public String getFollowed() {
        return followed;
    }

    public void setFollowed(String followed) {
        this.followed = followed;
    }

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }
}

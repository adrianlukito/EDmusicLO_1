package edu.bluejack16_2.edmusiclo.model;

import android.database.Cursor;
import android.media.MediaPlayer;
import android.provider.MediaStore;

/**
 * Created by Asus on 6/22/2017.
 */

public class MusicCursor {


    public static MusicCursor instance;



    public  static MusicCursor getInstance(){
        if(instance == null)
            instance = new MusicCursor();
        return  instance;
    }

    public Cursor musiccursor;
    public MusicCursor() {

    }


}

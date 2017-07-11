package edu.bluejack16_2.edmusiclo.model;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.drawable.VectorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.Vector;

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
    public Cursor genreCursor;

    public Vector<String> genreString;

    public MusicCursor() {
        genreString = new Vector<String>();
    }

    public void setGenreCursor(Activity activity, String condition){
        genreString.clear();
        musiccursor.moveToFirst();
        do {
            Uri uri = MediaStore.Audio.Genres.getContentUriForAudioId("external", Integer.parseInt(musiccursor.getString(0)));
            String condi = "";
            if (condition.equals("")) {
                genreCursor = activity.getContentResolver().query(uri, new String[]{MediaStore.Audio.Genres.NAME,
                        MediaStore.Audio.Genres._ID}, null, null, MediaStore.Audio.Genres.NAME + " ASC");
            } else {
                condi = MediaStore.Audio.Genres.NAME + " = " + condition;
                genreCursor = activity.getContentResolver().query(uri, new String[]{MediaStore.Audio.Genres.NAME,
                        MediaStore.Audio.Genres._ID}, condi, null, MediaStore.Audio.Genres.NAME + " ASC");
            }


            while(genreCursor.moveToNext()) {
                genreString.add(genreCursor.getString(genreCursor.getPosition()));
            }
        }while (true);
    }

}

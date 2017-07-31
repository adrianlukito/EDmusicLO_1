package edu.bluejack16_2.edmusiclo.state;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DatabaseReference;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

import edu.bluejack16_2.edmusiclo.SongFragment;
import edu.bluejack16_2.edmusiclo.model.MusicCursor;
import edu.bluejack16_2.edmusiclo.model.Session;
import edu.bluejack16_2.edmusiclo.model.Timeline;

/**
 * Created by Asus on 6/23/2017.
 */

public class ContextStateMusic {

    private static ContextStateMusic instance;

    StateMusicPlaying stateMusicPlaying;



    public static ContextStateMusic getInstance(){
        if(instance == null){
            instance = new ContextStateMusic();
        }
        return instance;
    }

    public void saveFirebaseTimeline(DatabaseReference databaseReference, Context context){
        Session session = new Session(context);

        Timeline timeline = new Timeline();

        timeline.setUser(session.getUser().getEmail());

        timeline.setSongName(MusicCursor.getInstance().musiccursor.getString(6));
        String id = databaseReference.push().getKey();

        timeline.setId(id);
        databaseReference.child(id).setValue(timeline);
    }

    public void play(int position){
        MusicCursor.getInstance().musiccursor.moveToPosition(position);

            String path =  MusicCursor.getInstance().musiccursor.getString(1);

            if (SongFragment.mediaPlayer != null) {
                SongFragment.mediaPlayer.release();
            }

            SongFragment.mediaPlayer = new MediaPlayer();
            try {
                SongFragment.mediaPlayer.setDataSource(path);
                SongFragment.mediaPlayer.prepare();
                SongFragment.mediaPlayer.start();
        } catch (IOException e) {
                Log.d("testa", e.toString());
            e.printStackTrace();
        }
        SongFragment.tvBottomSongTitle.setText(MusicCursor.getInstance().musiccursor.getString(6));
        SongFragment.tvBottomSongArtist.setText(MusicCursor.getInstance().musiccursor.getString(5));
    }

    public Drawable getIcon(View view){
        return stateMusicPlaying.getIcon(view);
    }

    private ContextStateMusic() {
        stateMusicPlaying = new NormalState();
    }

    public void next(){
        stateMusicPlaying = stateMusicPlaying.next();
    }

    public int nextMusic(){
        return stateMusicPlaying.nextMusic();
    }

    public int prevMusic(){
        return stateMusicPlaying.prevMusic();
    }

    public int onFinish(){
        return stateMusicPlaying.onFinish();
    }
}

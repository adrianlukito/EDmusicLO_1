package edu.bluejack16_2.edmusiclo.state;

import android.graphics.drawable.Drawable;
import android.view.View;

import java.util.concurrent.CopyOnWriteArrayList;

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

package edu.bluejack16_2.edmusiclo.state;

/**
 * Created by Asus on 6/23/2017.
 */

public class MusicContext {

    private StateMusicPlaying stateMusicPlaying;

    public void next(){
        stateMusicPlaying = stateMusicPlaying.next();
    }



}

package edu.bluejack16_2.edmusiclo;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class MusicActivity extends AppCompatActivity implements View.OnClickListener{

    ViewPager musicViewPager;

    TextView tvSongTitle, tvProgressTime, tvMaxTime;

    Drawable imgPlay, imgPause, imgNext, imgPrev;

    Button btnPlayPause;

    SeekBar seekBar;
    Handler handler = new Handler();

    String changerMilliscondToMinuteAndMinuteString(long mili){

        long timeMinute = TimeUnit.MILLISECONDS.toMinutes(mili);
        long timeSecond = TimeUnit.MILLISECONDS.toSeconds(mili)-TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(mili));

        return String.format("%02d:%02d", timeMinute, timeSecond);
    }

    public void setMoveSeekbar(){
        seekBar.setProgress(SongFragment.mediaPlayer.getCurrentPosition());
        Log.d("SecondddJalan", changerMilliscondToMinuteAndMinuteString(SongFragment.mediaPlayer.getCurrentPosition()));

        tvProgressTime.setText(changerMilliscondToMinuteAndMinuteString(SongFragment.mediaPlayer.getCurrentPosition()));
        handler.postDelayed(moveSeekBar, 1000);
    }

    Runnable moveSeekBar = new Runnable(){
        @Override
        public void run() {
            setMoveSeekbar();
        }
    };

    public void init(){
        musicViewPager = (ViewPager) findViewById(R.id.musicViewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new MusicDisplayFragment(),"Music Display");
        viewPagerAdapter.addFragment(new MusicLyricFragment(),"Music Lyrics");

        Typeface varela = Typeface.createFromAsset(getAssets(),"VarelaRound-Regular.ttf");

        imgPlay = getResources().getDrawable(R.drawable.ic_play);
        imgPause = getResources().getDrawable(R.drawable.ic_pause);

        btnPlayPause = (Button) findViewById(R.id.buttonPlay);
        btnPlayPause.setOnClickListener(this);
        musicViewPager.setAdapter(viewPagerAdapter);

        tvProgressTime = (TextView) findViewById(R.id.tvMusicProgressTime);
        tvMaxTime = (TextView) findViewById(R.id.tvMusicMaxTime);
        tvMaxTime.setText(changerMilliscondToMinuteAndMinuteString(SongFragment.mediaPlayer.getDuration()));

        tvProgressTime.setText(0+"");
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMax(SongFragment.mediaPlayer.getDuration());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        init();
        setMoveSeekbar();
//    try{
//
//
//
//
//        Thread t = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    MediaPlayer nowPlaying = SongFragment.mediaPlayer;
//                    while (nowPlaying.isPlaying() && nowPlaying.getDuration() > nowPlaying.getCurrentPosition()) {
//                        float progress = (nowPlaying.getCurrentPosition() / nowPlaying.getDuration());
//                        Log.d("lagi jalan",nowPlaying.getCurrentPosition()+" " + nowPlaying.getDuration()+ " "+ progress+"");
//                        seekBar.setProgress((int) progress);
//
//                        long mili = nowPlaying.getCurrentPosition();
//
//                        long timeMinute = TimeUnit.MILLISECONDS.toMinutes(mili);
//                        long timeSecond = TimeUnit.MILLISECONDS.toSeconds(mili);
//
//                        //tvProgressTime.setText(timeMinute + ":" + timeSecond);
//                    }
//                }
//            });
//
//            t.start();
//        }catch (Exception e){
//            Log.d("Errror", e.toString());
//        }
//        Toast.makeText(this, getIntent().getExtras().getInt("duration")+"", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        if(view == btnPlayPause){
            btnPlayPause.setBackground(imgPause);
        }
    }
}

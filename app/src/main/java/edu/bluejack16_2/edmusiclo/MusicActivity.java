package edu.bluejack16_2.edmusiclo;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

import edu.bluejack16_2.edmusiclo.model.MusicCursor;
import edu.bluejack16_2.edmusiclo.state.ContextStateMusic;
import edu.bluejack16_2.edmusiclo.state.NormalState;
import edu.bluejack16_2.edmusiclo.state.StateMusicPlaying;

public class MusicActivity extends AppCompatActivity implements View.OnClickListener, MediaPlayer.OnCompletionListener{

    ViewPager musicViewPager;

    TextView tvSongTitle, tvProgressTime, tvMaxTime;

    Drawable imgPlay, imgPause, imgNext, imgPrev;

    Button btnPlayPause, btnNext, btnPrev;

    SeekBar seekBar;
    Handler handler = new Handler();

    Boolean playState = true;

    String changerMilliscondToMinuteAndMinuteString(long mili){

        long timeMinute = TimeUnit.MILLISECONDS.toMinutes(mili);
        long timeSecond = TimeUnit.MILLISECONDS.toSeconds(mili)-TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(mili));

        return String.format("%02d:%02d", timeMinute, timeSecond);
    }

    public void setMoveSeekbar(){
        if(SongFragment.mediaPlayer != null && SongFragment.mediaPlayer.isPlaying()) {
            seekBar.setProgress(SongFragment.mediaPlayer.getCurrentPosition());
            tvProgressTime.setText(changerMilliscondToMinuteAndMinuteString(SongFragment.mediaPlayer.getCurrentPosition()));
        }
        handler.postDelayed(moveSeekBar, 100);
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
        btnPlayPause.setBackground(imgPause);
        btnPrev = (Button) findViewById(R.id.buttonPrev);
        btnPrev.setOnClickListener(this);
        btnNext = (Button) findViewById(R.id.buttonNext);
        btnNext.setOnClickListener(this);

        musicViewPager.setAdapter(viewPagerAdapter);

        tvSongTitle = (TextView) findViewById(R.id.tvMusicTitle);
        tvSongTitle.setText(MusicCursor.getInstance().musiccursor.getString(6));
        tvProgressTime = (TextView) findViewById(R.id.tvMusicProgressTime);
        tvMaxTime = (TextView) findViewById(R.id.tvMusicMaxTime);


        SongFragment.mediaPlayer.setOnCompletionListener(this);
        tvProgressTime.setText(0+"");
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMax(SongFragment.mediaPlayer.getDuration());
        tvMaxTime.setText(changerMilliscondToMinuteAndMinuteString(SongFragment.mediaPlayer.getDuration()));
        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_MOVE :

                        seekBar.setProgress(seekBar.getProgress());
                        tvProgressTime.setText(changerMilliscondToMinuteAndMinuteString(seekBar.getProgress()));

                        return false;
                    case MotionEvent.ACTION_UP:

                        SongFragment.mediaPlayer.seekTo(seekBar.getProgress());
                        if(!SongFragment.mediaPlayer.isPlaying()){
                            SongFragment.mediaPlayer.start();
                        }
                        return false;
                }
                return true;
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        init();
        setMoveSeekbar();
    }

    @Override
    public void onClick(View view) {
        if(view == btnPlayPause){
            if(SongFragment.mediaPlayer.isPlaying()) {
                btnPlayPause.setBackground(imgPlay);
                SongFragment.mediaPlayer.pause();
                playState = false;
            }else{
                playState=true;
                btnPlayPause.setBackground(imgPause);
                try {
                    SongFragment.mediaPlayer.start();
                }catch (Exception e){}
            }
        }else if(view == btnNext){
            MusicCursor.getInstance().musiccursor.moveToPosition(ContextStateMusic.getInstance().nextMusic());
            playSong();
            tvProgressTime.setText(changerMilliscondToMinuteAndMinuteString(0));
        }else if(view == btnPrev){
            MusicCursor.getInstance().musiccursor.moveToPosition(ContextStateMusic.getInstance().prevMusic());
            playSong();
            tvProgressTime.setText(changerMilliscondToMinuteAndMinuteString(0));
        }
    }

    private void playSong(){
        try {
            String path =  MusicCursor.getInstance().musiccursor.getString(1);

            tvSongTitle.setText(MusicCursor.getInstance().musiccursor.getString(6));
            if (SongFragment.mediaPlayer != null) {
                SongFragment.mediaPlayer.release();
            }

            SongFragment.mediaPlayer = new MediaPlayer();
            SongFragment.mediaPlayer.setDataSource(path);
            SongFragment.mediaPlayer.prepare();
            if(playState == true) {
                SongFragment.mediaPlayer.start();
                tvMaxTime.setText(changerMilliscondToMinuteAndMinuteString(SongFragment.mediaPlayer.getDuration()));
                seekBar.setMax(SongFragment.mediaPlayer.getDuration());
                SongFragment.mediaPlayer.setOnCompletionListener(this);
            }
        }catch (Exception e){
            Log.d("Errror", e.toString());
        }
    }
    @Override
    public void onCompletion(MediaPlayer mp) {

        Toast.makeText(this, "asdfasfasdfasfd", Toast.LENGTH_SHORT).show();
        MusicCursor.getInstance().musiccursor.moveToPosition(ContextStateMusic.getInstance().onFinish());
        playSong();
        tvProgressTime.setText(changerMilliscondToMinuteAndMinuteString(0));
    }
}

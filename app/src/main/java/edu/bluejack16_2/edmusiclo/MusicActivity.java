package edu.bluejack16_2.edmusiclo;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MusicActivity extends AppCompatActivity implements View.OnClickListener{

    ViewPager musicViewPager;

    TextView tvSongTitle, tvProgressTime, tvMaxTime;

    Drawable imgPlay, imgPause, imgNext, imgPrev;

    Button btnPlayPause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

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
    }

    @Override
    public void onClick(View view) {
        if(view == btnPlayPause){
            btnPlayPause.setBackground(imgPause);
        }
    }
}

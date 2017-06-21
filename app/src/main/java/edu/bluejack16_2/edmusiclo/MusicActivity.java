package edu.bluejack16_2.edmusiclo;

import android.graphics.Typeface;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MusicActivity extends AppCompatActivity {

    ViewPager musicViewPager;

    TextView tvSongTitle, tvProgressTime, tvMaxTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        musicViewPager = (ViewPager) findViewById(R.id.musicViewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new MusicDisplayFragment(),"Music Display");
        viewPagerAdapter.addFragment(new MusicLyricFragment(),"Music Lyrics");

        Typeface varela = Typeface.createFromAsset(getAssets(),"VarelaRound-Regular.ttf");

        musicViewPager.setAdapter(viewPagerAdapter);
    }
}

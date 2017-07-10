package edu.bluejack16_2.edmusiclo;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class GenreDetailActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tvDiscoverGenreDetailTitle;

    Button btnBackGenreDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_detail);

        btnBackGenreDetail = (Button) findViewById(R.id.backGenreDetail);
        btnBackGenreDetail.setOnClickListener(this);

        Typeface varela = Typeface.createFromAsset(getAssets(),"VarelaRound-Regular.ttf");

        tvDiscoverGenreDetailTitle = (TextView) findViewById(R.id.tvDiscoverGenreDetailTitle);
        tvDiscoverGenreDetailTitle.setText(getIntent().getStringExtra("genreName"));
        tvDiscoverGenreDetailTitle.setTypeface(varela);

        ListView genreDetailListView = (ListView) findViewById(R.id.genreDetailListView);

        final SongListViewAdapter songListViewAdapter = new SongListViewAdapter(getApplicationContext());

        songListViewAdapter.addSongList("Mr. Chu","A Pink","Pink Memories");
        songListViewAdapter.addSongList("A Sky Full Of Stars","Coldplay","Ghost Stories");
        songListViewAdapter.addSongList("All Of Me","John Legend","Love In The Future");
        songListViewAdapter.addSongList("Alone","Marshmello","Alone-Single");
        songListViewAdapter.addSongList("Bad Blood","Taylor Swift","1989");
        songListViewAdapter.addSongList("Bae Bae","BIGBANG","MADE");
        songListViewAdapter.addSongList("Beautiful Now","Zedd","True Colors");
        songListViewAdapter.addSongList("Blame It On Me","George Ezra","Wanted on Voyage");
        songListViewAdapter.addSongList("BLING BLING","iKON","NEW KIDS : BEGIN");
        songListViewAdapter.addSongList("CALL ME BABY","EXO","The 2nd Album 'EXODUS'");
        songListViewAdapter.addSongList("CHEER UP","TWICE","PAGE TWO");
        songListViewAdapter.addSongList("Crayon","G-Dragon","Crayon");
        songListViewAdapter.addSongList("CROOKED","G-Dragon","Coup D'etat");

        genreDetailListView.setAdapter(songListViewAdapter);
    }

    @Override
    public void onClick(View view) {
        if(view == btnBackGenreDetail){
            finish();
        }
    }
}

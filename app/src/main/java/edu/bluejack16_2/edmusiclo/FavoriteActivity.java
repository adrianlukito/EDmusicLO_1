package edu.bluejack16_2.edmusiclo;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.Touch;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import static edu.bluejack16_2.edmusiclo.R.id.tvDiscoverAlbumDetailTitle;

public class FavoriteActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tvDiscoverFavoriteTitle;

    Button btnBackFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        btnBackFavorite = (Button) findViewById(R.id.backFavorite);
        btnBackFavorite.setOnClickListener(this);

        Typeface varela = Typeface.createFromAsset(getAssets(),"VarelaRound-Regular.ttf");

        tvDiscoverFavoriteTitle = (TextView) findViewById(R.id.tvDiscoverFavoriteTitle);

        tvDiscoverFavoriteTitle.setTypeface(varela);

        ListView favoriteListView = (ListView) findViewById(R.id.favoriteListView);

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

        favoriteListView.setAdapter(songListViewAdapter);

    }

    @Override
    public void onClick(View view) {
        if(view == btnBackFavorite){
            finish();
        }
    }
}

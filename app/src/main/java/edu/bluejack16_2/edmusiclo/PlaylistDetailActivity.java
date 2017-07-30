package edu.bluejack16_2.edmusiclo;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import static edu.bluejack16_2.edmusiclo.R.id.tvDiscoverAlbumDetailTitle;

public class PlaylistDetailActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tvPlaylistDetailTitle;

    Button btnBackPlaylistDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_detail);

        btnBackPlaylistDetail = (Button) findViewById(R.id.backPlaylistDetail);
        btnBackPlaylistDetail.setOnClickListener(this);

        Typeface varela = Typeface.createFromAsset(getAssets(),"VarelaRound-Regular.ttf");

        tvPlaylistDetailTitle = (TextView) findViewById(R.id.tvPlaylistDetailTitle);

        tvPlaylistDetailTitle.setTypeface(varela);

        ListView playlistDetailListView = (ListView) findViewById(R.id.playlistDetailListView);

        final SongListViewAdapter songListViewAdapter = new SongListViewAdapter(getApplicationContext());

        songListViewAdapter.addSongList("That's What I Like","Bruno Mars","24k Magic");
        songListViewAdapter.addSongList("That's What I Like","Bruno Mars","24k Magic");
        songListViewAdapter.addSongList("That's What I Like","Bruno Mars","24k Magic");
        songListViewAdapter.addSongList("That's What I Like","Bruno Mars","24k Magic");
        songListViewAdapter.addSongList("That's What I Like","Bruno Mars","24k Magic");

        playlistDetailListView.setAdapter(songListViewAdapter);
    }

    @Override
    public void onClick(View view) {

    }
}

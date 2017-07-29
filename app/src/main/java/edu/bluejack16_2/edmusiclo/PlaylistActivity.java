package edu.bluejack16_2.edmusiclo;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PlaylistActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tvPlaylistTitle, tvCreateNewPlaylist;

    Button backPlaylist;

    RelativeLayout addNewPlayListLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        Typeface varela = Typeface.createFromAsset(getAssets(),"VarelaRound-Regular.ttf");

        tvPlaylistTitle = (TextView) findViewById(R.id.tvPlaylistTitle);
        tvCreateNewPlaylist = (TextView) findViewById(R.id.tvCreateNewPlaylist);

        tvPlaylistTitle.setTypeface(varela);
        tvCreateNewPlaylist.setTypeface(varela);

        backPlaylist = (Button) findViewById(R.id.backPlaylist);
        backPlaylist.setOnClickListener(this);

        addNewPlayListLayout = (RelativeLayout) findViewById(R.id.addNewPlayListLayout);
        addNewPlayListLayout.setOnClickListener(this);

        ListView playlistListView = (ListView) findViewById(R.id.playlistListView);

        final PlaylistListViewAdapter playlistListViewAdapter = new PlaylistListViewAdapter(getApplicationContext());

        playlistListViewAdapter.addPlaylist("K-POP","100 songs");
        playlistListViewAdapter.addPlaylist("Instrumental","12 songs");
        playlistListViewAdapter.addPlaylist("My Jam","25 songs");
        playlistListViewAdapter.addPlaylist("Anime","14 songs");

        playlistListView.setAdapter(playlistListViewAdapter);

        playlistListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), PlaylistDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view == backPlaylist){
            finish();
        }else if(view == addNewPlayListLayout) {
            Intent intent = new Intent(getApplicationContext(), CreateNewPlaylistActivity.class);
            startActivity(intent);
        }
    }
}

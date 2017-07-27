package edu.bluejack16_2.edmusiclo;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

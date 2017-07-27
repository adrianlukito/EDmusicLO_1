package edu.bluejack16_2.edmusiclo;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class CreateNewPlaylistActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tvCreateNewPlaylistTitle;

    Button backCreateNewPlaylist,saveNewPlaylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_playlist);

        Typeface varela = Typeface.createFromAsset(getAssets(),"VarelaRound-Regular.ttf");

        tvCreateNewPlaylistTitle = (TextView) findViewById(R.id.tvCreateNewPlaylistTitle);

        tvCreateNewPlaylistTitle.setTypeface(varela);

        backCreateNewPlaylist = (Button) findViewById(R.id.backCreateNewPlaylist);
        saveNewPlaylist = (Button) findViewById(R.id.saveNewPlaylist);
        backCreateNewPlaylist.setOnClickListener(this);
        saveNewPlaylist.setOnClickListener(this);

        ListView createNewPlaylistListView = (ListView) findViewById(R.id.createNewPlaylistListView);

        final CreateNewPlaylistAdapter createNewPlaylistAdapter = new CreateNewPlaylistAdapter(getApplicationContext());

        createNewPlaylistAdapter.addSongList("Mr. Chu","A Pink","Pink Memories");
        createNewPlaylistAdapter.addSongList("A Sky Full Of Stars","Coldplay","Ghost Stories");
        createNewPlaylistAdapter.addSongList("All Of Me","John Legend","Love In The Future");
        createNewPlaylistAdapter.addSongList("Alone","Marshmello","Alone-Single");
        createNewPlaylistAdapter.addSongList("Bad Blood","Taylor Swift","1989");
        createNewPlaylistAdapter.addSongList("Bae Bae","BIGBANG","MADE");
        createNewPlaylistAdapter.addSongList("Beautiful Now","Zedd","True Colors");
        createNewPlaylistAdapter.addSongList("Blame It On Me","George Ezra","Wanted on Voyage");
        createNewPlaylistAdapter.addSongList("BLING BLING","iKON","NEW KIDS : BEGIN");
        createNewPlaylistAdapter.addSongList("CALL ME BABY","EXO","The 2nd Album 'EXODUS'");
        createNewPlaylistAdapter.addSongList("CHEER UP","TWICE","PAGE TWO");
        createNewPlaylistAdapter.addSongList("Crayon","G-Dragon","Crayon");
        createNewPlaylistAdapter.addSongList("CROOKED","G-Dragon","Coup D'etat");

        createNewPlaylistListView.setAdapter(createNewPlaylistAdapter);
    }

    @Override
    public void onClick(View view) {
        if(view == backCreateNewPlaylist){
            finish();
        }else if(view == saveNewPlaylist){
            View inputNewPlaylistView = LayoutInflater.from(this).inflate(R.layout.input_new_playlist,null);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(inputNewPlaylistView);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            AlertDialog alert = builder.create();
            alert.getWindow().setGravity(Gravity.CENTER);
            alert.show();
        }
    }
}

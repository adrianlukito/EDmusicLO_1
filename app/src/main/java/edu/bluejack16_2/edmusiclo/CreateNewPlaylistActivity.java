package edu.bluejack16_2.edmusiclo;

import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.api.BooleanResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Vector;

import edu.bluejack16_2.edmusiclo.model.FavoriteSong;
import edu.bluejack16_2.edmusiclo.model.MusicCursor;
import edu.bluejack16_2.edmusiclo.model.PlaylistSong;
import edu.bluejack16_2.edmusiclo.model.Session;

public class CreateNewPlaylistActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tvCreateNewPlaylistTitle;

    Button backCreateNewPlaylist,saveNewPlaylist;

    Vector<Integer> position;
    CreateNewPlaylistAdapter createNewPlaylistAdapter = null;
    DatabaseReference databaseReference;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_playlist);

        Typeface varela = Typeface.createFromAsset(getAssets(),"VarelaRound-Regular.ttf");

        databaseReference = FirebaseDatabase.getInstance().getReference("Playlist");

        tvCreateNewPlaylistTitle = (TextView) findViewById(R.id.tvCreateNewPlaylistTitle);

        tvCreateNewPlaylistTitle.setTypeface(varela);

        backCreateNewPlaylist = (Button) findViewById(R.id.backCreateNewPlaylist);
        saveNewPlaylist = (Button) findViewById(R.id.saveNewPlaylist);
        backCreateNewPlaylist.setOnClickListener(this);
        saveNewPlaylist.setOnClickListener(this);

        ListView createNewPlaylistListView = (ListView) findViewById(R.id.createNewPlaylistListView);

        createNewPlaylistAdapter = new CreateNewPlaylistAdapter(getApplicationContext());

        String[] proj = {MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Video.Media.SIZE,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.ARTIST_ID};

        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0 AND " + MediaStore.Audio.Media.DATA + " Like '%.mp3'";

        cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                proj, selection, null, MediaStore.Audio.Media.TITLE + " ASC");

        cursor.moveToFirst();
        while(cursor.moveToNext()){
            createNewPlaylistAdapter.addSongList(cursor.getString(6),
                    cursor.getString(5), cursor.getString(4));
        }

        createNewPlaylistListView.setAdapter(createNewPlaylistAdapter);
    }

    @Override
    public void onClick(View view) {
        if(view == backCreateNewPlaylist){
            finish();
        }else if(view == saveNewPlaylist){

            final View inputNewPlaylistView = LayoutInflater.from(this).inflate(R.layout.input_new_playlist,null);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(inputNewPlaylistView);

            final EditText newPlaylistName = (EditText) inputNewPlaylistView.findViewById(R.id.newPlaylistName);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    try {
                        ArrayList<Boolean> isChecked = createNewPlaylistAdapter.getPlayListCheck();
                        String key = databaseReference.push().getKey();
                        PlaylistSong playlistSong = new PlaylistSong();
                        Session session = new Session(getBaseContext());
                        playlistSong.setEmail(session.getUser().getEmail());
                        playlistSong.setName(newPlaylistName.getText().toString());

                        Log.d("testa", newPlaylistName.getText().toString()+"");
                        for (int j = 0; j < isChecked.size(); j++) {
                            if (isChecked.get(j)) {
                                cursor.moveToPosition(j);
                                playlistSong.addIdSong(Integer.parseInt(cursor.getString(0)));
                            }
                        }
                        databaseReference.child(key).setValue(playlistSong);
                        finish();
                    }catch (Exception e){
                        Log.d("testa", e.toString());
                    }
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

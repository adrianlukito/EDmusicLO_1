package edu.bluejack16_2.edmusiclo;

import android.database.Cursor;
import android.graphics.Typeface;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Selection;
import android.text.TextUtils;
import android.text.method.Touch;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Vector;

import edu.bluejack16_2.edmusiclo.model.MusicCursor;
import edu.bluejack16_2.edmusiclo.model.Session;

import static edu.bluejack16_2.edmusiclo.R.id.tvDiscoverAlbumDetailTitle;

public class FavoriteActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tvDiscoverFavoriteTitle;

    Button btnBackFavorite;

    DatabaseReference favoriteDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        btnBackFavorite = (Button) findViewById(R.id.backFavorite);
        btnBackFavorite.setOnClickListener(this);

        Typeface varela = Typeface.createFromAsset(getAssets(),"VarelaRound-Regular.ttf");

        tvDiscoverFavoriteTitle = (TextView) findViewById(R.id.tvDiscoverFavoriteTitle);

        tvDiscoverFavoriteTitle.setTypeface(varela);

        final ListView favoriteListView = (ListView) findViewById(R.id.favoriteListView);

        final SongListViewAdapter songListViewAdapter = new SongListViewAdapter(getApplicationContext());


        Session session = new Session(getBaseContext());
        favoriteDatabaseReference = FirebaseDatabase.getInstance().getReference("Favorite");

        final Vector<String> idSongs = new Vector<String>();

        favoriteDatabaseReference.orderByChild("emailUser").equalTo(session.getUser().getEmail()).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        try {
                            for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                String thisEmail = childSnapshot.child("songID").getValue().toString();
                                Cursor tempCursor;
                                idSongs.add(""+thisEmail+"");

                                Log.d("textn", thisEmail+" as");
                                Log.d("text", idSongs.get(0));
                            }
                            String[] mutInteger = idSongs.toArray(new String[idSongs.size()]);

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
                            String a = TextUtils.join(", ", mutInteger);

                            Log.d("text", a);

                            String selection = MediaStore.Audio.Media._ID + " in (" + a+ ")";

                            Log.d("text", selection);

                            Cursor musicCursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                                    proj, selection, null, MediaStore.Audio.Media.TITLE + " ASC");

                            while (musicCursor.moveToNext()) {
                                Log.d("text", "Asdf");
                                songListViewAdapter.addSongList(musicCursor.getString(6),
                                        musicCursor.getString(5), musicCursor.getString(4));

                            }

                            favoriteListView.setAdapter(songListViewAdapter);
                        }catch (Exception e){
                            Log.d("Errror", e.toString());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );

//        songListViewAdapter.addSongList("Mr. Chu","A Pink","Pink Memories");
//        songListViewAdapter.addSongList("A Sky Full Of Stars","Coldplay","Ghost Stories");
//        songListViewAdapter.addSongList("All Of Me","John Legend","Love In The Future");
//        songListViewAdapter.addSongList("Alone","Marshmello","Alone-Single");
//        songListViewAdapter.addSongList("Bad Blood","Taylor Swift","1989");
//        songListViewAdapter.addSongList("Bae Bae","BIGBANG","MADE");
//        songListViewAdapter.addSongList("Beautiful Now","Zedd","True Colors");
//        songListViewAdapter.addSongList("Blame It On Me","George Ezra","Wanted on Voyage");
//        songListViewAdapter.addSongList("BLING BLING","iKON","NEW KIDS : BEGIN");
//        songListViewAdapter.addSongList("CALL ME BABY","EXO","The 2nd Album 'EXODUS'");
//        songListViewAdapter.addSongList("CHEER UP","TWICE","PAGE TWO");
//        songListViewAdapter.addSongList("Crayon","G-Dragon","Crayon");
//        songListViewAdapter.addSongList("CROOKED","G-Dragon","Coup D'etat");


    }

    @Override
    public void onClick(View view) {
        if(view == btnBackFavorite){
            finish();
        }
    }
}

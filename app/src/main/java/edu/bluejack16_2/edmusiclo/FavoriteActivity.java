package edu.bluejack16_2.edmusiclo;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Selection;
import android.text.TextUtils;
import android.text.method.Touch;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import edu.bluejack16_2.edmusiclo.state.ContextStateMusic;

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

                            final Cursor musicCursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                                    proj, selection, null, MediaStore.Audio.Media.TITLE + " ASC");

                            while (musicCursor.moveToNext()) {
                                Log.d("text", "Asdf");
                                songListViewAdapter.addSongList(musicCursor.getString(6),
                                        musicCursor.getString(5), musicCursor.getString(4));
                            }

                            favoriteListView.setAdapter(songListViewAdapter);


                            favoriteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    TextView title = (TextView) view.findViewById(R.id.tvSongAlbum);
                                    title.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                                    title.setMarqueeRepeatLimit(-1);
                                    title.setSelected(true);
                                    MusicCursor.getInstance().musiccursor = musicCursor;

                                    playingSong(position);

                                    SongFragment.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                        @Override
                                        public void onCompletion(MediaPlayer mp) {
                                            int position = ContextStateMusic.getInstance().onFinish();
                                            MusicCursor.getInstance().musiccursor.moveToPosition(position);
                                            playingSong(position);
                                        }
                                    });

                                    ContextStateMusic.getInstance().saveFirebaseTimeline(FirebaseDatabase.getInstance().getReference("Timeline"), getBaseContext());


                                    Intent intent = new Intent(FavoriteActivity.this, MusicActivity.class);
                                    intent.putExtra("position", MusicCursor.getInstance().musiccursor.getPosition());
                                    intent.putExtra("duration", SongFragment.mediaPlayer.getCurrentPosition());
                                    startActivity(intent);
                                }
                            });
                        }catch (Exception e){
                            Log.d("Errror", e.toString());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );

    }
    void playingSong(int position){

        MusicCursor.getInstance().musiccursor.moveToPosition(position);
        String path =  MusicCursor.getInstance().musiccursor.getString(1);
        try {
            if (SongFragment.mediaPlayer != null) {
                SongFragment.mediaPlayer.stop();
            }
            SongFragment.mediaPlayer = new MediaPlayer();
            SongFragment.mediaPlayer.setDataSource(path);
            SongFragment.mediaPlayer.prepare();
        }catch (Exception e){

        }
        SongFragment.mediaPlayer.start();

        ContextStateMusic.getInstance().saveFirebaseTimeline(FirebaseDatabase.getInstance().getReference("Timeline"), getBaseContext());

    }
    @Override
    public void onClick(View view) {
        if(view == btnBackFavorite){
            finish();
        }
    }
}

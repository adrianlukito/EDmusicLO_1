package edu.bluejack16_2.edmusiclo;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

import java.util.Vector;

import edu.bluejack16_2.edmusiclo.model.MusicCursor;
import edu.bluejack16_2.edmusiclo.model.PlaylistSong;
import edu.bluejack16_2.edmusiclo.model.Session;
import edu.bluejack16_2.edmusiclo.state.ContextStateMusic;

import static edu.bluejack16_2.edmusiclo.R.id.tvDiscoverAlbumDetailTitle;

public class PlaylistDetailActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tvPlaylistDetailTitle;
    TextView tvTotalSong;

    Button btnBackPlaylistDetail;

    DatabaseReference databaseReference;

    Session session;

    Vector<String> idSongs;
    Cursor musicCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_detail);


        btnBackPlaylistDetail = (Button) findViewById(R.id.backPlaylistDetail);
        btnBackPlaylistDetail.setOnClickListener(this);

        Typeface varela = Typeface.createFromAsset(getAssets(),"VarelaRound-Regular.ttf");

        tvPlaylistDetailTitle = (TextView) findViewById(R.id.tvPlaylistDetailTitle);
        tvTotalSong = (TextView) findViewById(R.id.tvTotalSong);

        tvPlaylistDetailTitle.setTypeface(varela);

        idSongs = new Vector<String>();

        final ListView playlistDetailListView = (ListView) findViewById(R.id.playlistDetailListView);

        final SongListViewAdapter songListViewAdapter = new SongListViewAdapter(getApplicationContext());

        databaseReference = FirebaseDatabase.getInstance().getReference("Playlist");

        session = new Session(getBaseContext());

        Intent intent = getIntent();
        final String key = intent.getExtras().getString("key");

        databaseReference.orderByChild("id").equalTo(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot childSnapshot : dataSnapshot.child(key).child("idSongs").getChildren()){
                    String song = childSnapshot.getValue().toString();
                    Cursor tempCursor;
                    idSongs.add(""+song+"");
                }
                try {
                    tvTotalSong.setText(idSongs.size() + " Songs");
                    String text = dataSnapshot.child(key).child("name").getValue().toString();
                    tvPlaylistDetailTitle.setText(text);
                }catch (Exception e){
                    Log.d("testa", e.toString());
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

                String selection = MediaStore.Audio.Media._ID + " in (" + a+ ")";

                musicCursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        proj, selection, null, MediaStore.Audio.Media.TITLE + " ASC");

                while (musicCursor.moveToNext()) {
                    songListViewAdapter.addSongList(musicCursor.getString(6),
                            musicCursor.getString(5), musicCursor.getString(4));
                }

                playlistDetailListView.setAdapter(songListViewAdapter);

                playlistDetailListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        try {
                            TextView title = (TextView) view.findViewById(R.id.tvSongAlbum);
                            title.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                            title.setMarqueeRepeatLimit(-1);
                            title.setSelected(true);
                            MusicCursor.getInstance().musiccursor = musicCursor;



                            ContextStateMusic.getInstance().play(position);
                            ContextStateMusic.getInstance().saveFirebaseTimeline(FirebaseDatabase.getInstance().getReference("Timeline"), getBaseContext());

                            Log.d("testa", position + " tesga "+ MusicCursor.getInstance().musiccursor.getString(5));
                            SongFragment.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    int position = ContextStateMusic.getInstance().onFinish();
                                    MusicCursor.getInstance().musiccursor.moveToPosition(position);
                                    ContextStateMusic.getInstance().play(position);
                                    ContextStateMusic.getInstance().saveFirebaseTimeline(databaseReference, getBaseContext());
                                }
                            });

//                            Intent intent = new Intent(PlaylistDetailActivity.this, MusicActivity.class);
//                            intent.putExtra("position", MusicCursor.getInstance().musiccursor.getPosition());
//                            intent.putExtra("duration", SongFragment.mediaPlayer.getCurrentPosition());
//                            startActivity(intent);
                        }catch (Exception e){
                            Log.d("testa", e.toString());
                        }
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}

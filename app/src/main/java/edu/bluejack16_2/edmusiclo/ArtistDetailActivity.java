package edu.bluejack16_2.edmusiclo;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.system.ErrnoException;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import edu.bluejack16_2.edmusiclo.model.MusicCursor;
import edu.bluejack16_2.edmusiclo.state.ContextStateMusic;

public class ArtistDetailActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tvDiscoverArtistTitle;

    Button btnBackArtistDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_artist);

        btnBackArtistDetail = (Button) findViewById(R.id.backArtistDetail);
        btnBackArtistDetail.setOnClickListener(this);

        Typeface varela = Typeface.createFromAsset(getAssets(),"VarelaRound-Regular.ttf");

        tvDiscoverArtistTitle = (TextView) findViewById(R.id.tvDiscoverArtistTitle);

        tvDiscoverArtistTitle.setTypeface(varela);

        ListView artistSongListView = (ListView) findViewById(R.id.artistSongListView);

        final ArtistSongListViewAdapter artistSongListViewAdapter = new ArtistSongListViewAdapter(getApplicationContext());
        try {
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
            Log.d("text",getIntent().getExtras().getString("artist"));
            String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0 AND " + MediaStore.Audio.Media.DATA + " Like '%.mp3' and "
                    + MediaStore.Audio.Media.ARTIST + " = '" + getIntent().getExtras().getString("artist")+"'";

            final Cursor musicCursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    proj, selection, null, MediaStore.Audio.Media.TITLE + " ASC");

            while (musicCursor.moveToNext()) {
                artistSongListViewAdapter.addArtistSongList(musicCursor.getString(6), musicCursor.getString(4));
            }
            artistSongListView.setAdapter(artistSongListViewAdapter);

            artistSongListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView title = (TextView) view.findViewById(R.id.tvSongAlbum);
                    title.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                    title.setMarqueeRepeatLimit(-1);
                    title.setSelected(true);
                    Log.d("testa", position+"");
                    MusicCursor.getInstance().musiccursor = musicCursor;

                    playingSong(position);
                    ContextStateMusic.getInstance().saveFirebaseTimeline(FirebaseDatabase.getInstance().getReference("Timeline"), getBaseContext());

                    SongFragment.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            try {
                                int position = ContextStateMusic.getInstance().nextMusic();
                                MusicCursor.getInstance().musiccursor.moveToPosition(position);
                                playingSong(position);
                            }catch (Exception e){
                            }
                        }
                    });
                    Intent intent = new Intent(getApplicationContext(), MusicActivity.class);
                    intent.putExtra("position", MusicCursor.getInstance().musiccursor.getPosition());
                    intent.putExtra("duration", SongFragment.mediaPlayer.getCurrentPosition());
                    startActivity(intent);
                }
            });
        }catch (Exception e){
            Log.d("text",e.toString());
        }
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
        Log.d("testa", path);
    }
    @Override
    public void onClick(View view) {
        if(view == btnBackArtistDetail){
            finish();
        }
    }


}

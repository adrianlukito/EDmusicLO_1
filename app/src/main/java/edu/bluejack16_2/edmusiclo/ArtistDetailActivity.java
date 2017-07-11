package edu.bluejack16_2.edmusiclo;

import android.database.Cursor;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import edu.bluejack16_2.edmusiclo.model.MusicCursor;

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
            Log.d("text",getIntent().getExtras().getString("artist_id"));
            String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0 AND " + MediaStore.Audio.Media.DATA + " Like '%.mp3' and "
                    + MediaStore.Audio.Media.ARTIST + " = '" + getIntent().getExtras().getString("artist")+"'";

            final Cursor musicCursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    proj, selection, null, MediaStore.Audio.Media.TITLE + " ASC");

            while (musicCursor.moveToNext()) {
                artistSongListViewAdapter.addArtistSongList(musicCursor.getString(6), musicCursor.getString(4));
            }
        }catch (Exception e){
            Log.d("text",e.toString());
        }
//        artistSongListViewAdapter.addArtistSongList("24K Magic (R3hab Remix)", "24K Magic (R3hab Remix)");
//        artistSongListViewAdapter.addArtistSongList("Versace On The Floor","Versace On The Floor (Bruno Mars vs. David)");
//        artistSongListViewAdapter.addArtistSongList("That's What I Like","That's What I Like (Alan Walker Remix)");
//        artistSongListViewAdapter.addArtistSongList("Uptown Funk","Uptown Funk");
//        artistSongListViewAdapter.addArtistSongList("Treasure","Summer Songs");
//        artistSongListViewAdapter.addArtistSongList("It Will Rain","Acoustic Rain");
//        artistSongListViewAdapter.addArtistSongList("When I Was Your Man","Now That's What I Call 30 Years");
//        artistSongListViewAdapter.addArtistSongList("Lock Out Of Heaven","Wine & Chocolates");
//        artistSongListViewAdapter.addArtistSongList("Just The Way You Are","Mum");
//        artistSongListViewAdapter.addArtistSongList("Nothin' On You","R&B Anthems");
//        artistSongListViewAdapter.addArtistSongList("The Lazy Song","The Lazy Song");
//        artistSongListViewAdapter.addArtistSongList("Grenade","The Grenade Sessions");
//        artistSongListViewAdapter.addArtistSongList("Talking To The Moon","Doo-Wops & Hooligans");
//        artistSongListViewAdapter.addArtistSongList("Count On Me","Doo-Wops & Hooligans");

        artistSongListView.setAdapter(artistSongListViewAdapter);
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
    }
    @Override
    public void onClick(View view) {
        if(view == btnBackArtistDetail){
            finish();
        }
    }
}

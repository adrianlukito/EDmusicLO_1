package edu.bluejack16_2.edmusiclo;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import edu.bluejack16_2.edmusiclo.model.MusicCursor;
import edu.bluejack16_2.edmusiclo.state.ContextStateMusic;

public class AlbumDetailActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tvDiscoverAlbumDetailTitle;

    Button btnBackAlbumDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_detail);

        btnBackAlbumDetail = (Button) findViewById(R.id.backAlbumDetail);
        btnBackAlbumDetail.setOnClickListener(this);

        Typeface varela = Typeface.createFromAsset(getAssets(),"VarelaRound-Regular.ttf");

        tvDiscoverAlbumDetailTitle = (TextView) findViewById(R.id.tvDiscoverAlbumDetailTitle);

        tvDiscoverAlbumDetailTitle.setTypeface(varela);

        ListView albumDetailListView = (ListView) findViewById(R.id.albumDetailListView);

        final SongListViewAdapter songListViewAdapter = new SongListViewAdapter(getApplicationContext());

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

        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0 AND " + MediaStore.Audio.Media.DATA + " Like '%.mp3' and "
                + MediaStore.Audio.Media.ALBUM_ID +" = " + getIntent().getExtras().getString("album_id");

       final Cursor musicCursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                proj, selection, null, MediaStore.Audio.Media.TITLE + " ASC");

        while (musicCursor.moveToNext()) {
            songListViewAdapter.addSongList(musicCursor.getString(6),
                    musicCursor.getString(5), musicCursor.getString(4));
        }
        albumDetailListView.setAdapter(songListViewAdapter);



        albumDetailListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                Intent intent = new Intent(AlbumDetailActivity.this, MusicActivity.class);
                intent.putExtra("position", MusicCursor.getInstance().musiccursor.getPosition());
                intent.putExtra("duration", SongFragment.mediaPlayer.getCurrentPosition());
                //Toast.makeText(getContext(), SongFragment.mediaPlayer.getCurrentPosition()+"", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
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
        if(view == btnBackAlbumDetail){
            finish();
        }
    }
}

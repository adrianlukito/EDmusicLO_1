package edu.bluejack16_2.edmusiclo;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import edu.bluejack16_2.edmusiclo.model.MusicCursor;
import edu.bluejack16_2.edmusiclo.state.ContextStateMusic;

public class GenreDetailActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tvDiscoverGenreDetailTitle;

    Button btnBackGenreDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_detail);

        btnBackGenreDetail = (Button) findViewById(R.id.backGenreDetail);
        btnBackGenreDetail.setOnClickListener(this);

        Typeface varela = Typeface.createFromAsset(getAssets(),"VarelaRound-Regular.ttf");

        tvDiscoverGenreDetailTitle = (TextView) findViewById(R.id.tvDiscoverGenreDetailTitle);
        tvDiscoverGenreDetailTitle.setText(getIntent().getStringExtra("genreName"));
        tvDiscoverGenreDetailTitle.setTypeface(varela);

        ListView genreDetailListView = (ListView) findViewById(R.id.genreDetailListView);

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

        String test = getIntent().getExtras().getString("genreID");

        Long genreID = Long.parseLong(test);

        Uri uri = MediaStore.Audio.Genres.Members.getContentUri("external", genreID);

        MusicCursor.getInstance().musiccursor = getContentResolver().query(uri, proj, null,null,null);
        final Cursor musicCursor = getContentResolver().query(uri,
                proj, null, null, MediaStore.Audio.Media.TITLE + " ASC");

        while (musicCursor.moveToNext()) {
            songListViewAdapter.addSongList(musicCursor.getString(6),
                    musicCursor.getString(5),musicCursor.getString(4));
        }

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

        genreDetailListView.setAdapter(songListViewAdapter);

        genreDetailListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                Intent intent = new Intent(GenreDetailActivity.this, MusicActivity.class);
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
        if(view == btnBackGenreDetail){
            finish();
        }
    }
}
